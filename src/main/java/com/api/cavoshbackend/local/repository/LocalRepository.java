package com.api.cavoshbackend.local.repository;

import com.api.cavoshbackend.local.model.Local;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {

    @EntityGraph(attributePaths = "distrito")
    List<Local> findByActivoTrueAndFrecuenteTrueOrderByNombreAscIdAsc();

    @EntityGraph(attributePaths = "distrito")
    List<Local> findByDistritoIdAndActivoTrueOrderByNombreAscIdAsc(Long distritoId);

}
