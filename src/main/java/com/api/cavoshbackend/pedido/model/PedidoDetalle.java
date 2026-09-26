package com.api.cavoshbackend.pedido.model;

import com.api.cavoshbackend.producto.model.OpcionPersonalizacion;
import com.api.cavoshbackend.producto.model.Producto;
import com.api.cavoshbackend.producto.model.ProductoSize;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pedido_detalles")
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_size_id", nullable = false)
    private ProductoSize productoSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milk_id")
    private OpcionPersonalizacion milk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cream_id")
    private OpcionPersonalizacion cream;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caffeine_id")
    private OpcionPersonalizacion caffeine;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    public PedidoDetalle(
            Pedido pedido,
            Producto producto,
            ProductoSize productoSize,
            OpcionPersonalizacion milk,
            OpcionPersonalizacion cream,
            OpcionPersonalizacion caffeine,
            Integer cantidad,
            BigDecimal precioUnitario
    ) {
        this.pedido = pedido;
        this.producto = producto;
        this.productoSize = productoSize;
        this.milk = milk;
        this.cream = cream;
        this.caffeine = caffeine;
        this.nombreProducto = producto.getNombre();
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
