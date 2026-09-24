package com.api.cavoshbackend.usuario.repository;

import com.api.cavoshbackend.usuario.enums.EstadoCodigo;
import com.api.cavoshbackend.usuario.model.CodigoVerificacion;
import com.api.cavoshbackend.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long> {

    List<CodigoVerificacion> findAllByUsuarioAndEstado(Usuario usuario, EstadoCodigo estado);

    Optional<CodigoVerificacion> findFirstByUsuarioAndEstadoOrderByFechaCreacionDesc(Usuario usuario, EstadoCodigo estado);
}
