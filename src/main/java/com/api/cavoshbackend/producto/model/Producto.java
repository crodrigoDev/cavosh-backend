package com.api.cavoshbackend.producto.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;

    @Column(name = "nuevo", nullable = false)
    private boolean nuevo;

    @Column(name = "frecuente", nullable = false)
    private boolean frecuente;

    @Column(name = "disponible", nullable = false)
    private boolean disponible;

    @Column(name = "personalizable", nullable = false)
    private boolean personalizable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Producto(
            String nombre,
            String descripcion,
            String imagenUrl,
            boolean frecuente,
            boolean personalizable,
            Categoria categoria
    ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.nuevo = true;
        this.frecuente = frecuente;
        this.disponible = true;
        this.personalizable = personalizable;
        this.categoria = categoria;
    }


}
