package com.api.cavoshbackend.producto.model;

import com.api.cavoshbackend.producto.enums.TipoPersonalizacion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "opciones_personalizacion")
public class OpcionPersonalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPersonalizacion tipo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio_extra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioExtra;

    @Column(name = "predeterminada", nullable = false)
    private boolean predeterminada;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    public OpcionPersonalizacion(
            TipoPersonalizacion tipo,
            String nombre,
            BigDecimal precioExtra,
            boolean predeterminada
    ) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precioExtra = precioExtra;
        this.predeterminada = predeterminada;
        this.activo = true;
    }
}
