package com.api.cavoshbackend.pedido.repository;

import com.api.cavoshbackend.pedido.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @EntityGraph(attributePaths = "local")
    List<Pedido> findByUsuarioIdOrderByFechaCreacionDescIdDesc(Long usuarioId);

    @EntityGraph(attributePaths = {"local", "local.distrito"})
    Optional<Pedido> findByIdAndUsuarioId(Long id, Long usuarioId);
}
