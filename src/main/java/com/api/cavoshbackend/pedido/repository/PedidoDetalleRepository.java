package com.api.cavoshbackend.pedido.repository;

import com.api.cavoshbackend.pedido.model.PedidoDetalle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

    @EntityGraph(attributePaths = {"productoSize", "milk", "cream", "caffeine"})
    List<PedidoDetalle> findByPedidoIdOrderByIdAsc(Long pedidoId);
}
