package com.api.cavoshbackend.producto.repository;

import com.api.cavoshbackend.producto.model.OpcionPersonalizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcionPersonalizacionRepository extends JpaRepository<OpcionPersonalizacion, Long> {

    List<OpcionPersonalizacion> findByActivoTrueOrderByTipoAscIdAsc();
}
