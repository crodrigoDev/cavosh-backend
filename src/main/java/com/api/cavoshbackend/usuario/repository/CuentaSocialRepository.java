package com.api.cavoshbackend.usuario.repository;

import com.api.cavoshbackend.usuario.enums.ProveedorSocial;
import com.api.cavoshbackend.usuario.model.CuentaSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaSocialRepository extends JpaRepository<CuentaSocial, Long> {

    Optional<CuentaSocial> findByProveedorAndIdProveedor(
            ProveedorSocial proveedor,
            String idProveedor
    );
}
