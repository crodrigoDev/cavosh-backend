package com.api.cavoshbackend.usuario.model;

import com.api.cavoshbackend.usuario.enums.EstadoCodigo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "codigo_verificacion")
public class CodigoVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", updatable = false, nullable = false, length = 64)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoCodigo estado;

    @Column(name = "fecha_expiracion", updatable = false, nullable = false)
    private Instant fechaExpiracion;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false, nullable = false)
    private Instant fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public CodigoVerificacion(
            String codigo,
            Instant fechaExpiracion,
            Usuario usuario
    ){
        this.codigo = codigo;
        this.estado = EstadoCodigo.VIGENTE;
        this.fechaExpiracion = fechaExpiracion;
        this.usuario = usuario;
    }


    public boolean estaExpirado(Instant ahora) {
        return !ahora.isBefore(fechaExpiracion);
    }

    public boolean coincideCon(String codigoRecibido) {
        return codigo.equals(codigoRecibido);
    }

    public void marcarUsado() {
        this.estado = EstadoCodigo.USADO;
    }

    public void marcarExpirado() {
        this.estado = EstadoCodigo.EXPIRADO;
    }
}
