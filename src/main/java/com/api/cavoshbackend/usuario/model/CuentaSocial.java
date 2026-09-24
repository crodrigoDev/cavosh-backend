package com.api.cavoshbackend.usuario.model;

import com.api.cavoshbackend.usuario.enums.ProveedorSocial;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(
        name = "cuenta_social",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_cuenta_social_proveedor_id",
                    columnNames = {"proveedor", "id_proveedor"}
            )
    })
public class CuentaSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_proveedor", updatable = false, nullable = false)
    private String idProveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "proveedor", updatable = false, nullable = false)
    private ProveedorSocial proveedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;
}
