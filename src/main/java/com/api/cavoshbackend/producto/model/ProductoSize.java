package com.api.cavoshbackend.producto.model;

import com.api.cavoshbackend.producto.enums.Size;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "producto_sizes")
public class ProductoSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false)
    private Size nombre;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "predeterminado", nullable = false)
    private boolean predeterminado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public ProductoSize(
            Size nombre,
            BigDecimal precio,
            boolean predeterminado,
            Producto producto
    ){
        this.nombre = nombre;
        this.precio = precio;
        this.predeterminado = predeterminado;
        this.producto = producto;
    }
}
