package com.api.cavoshbackend.pedido.model;

import com.api.cavoshbackend.local.model.Local;
import com.api.cavoshbackend.pedido.enums.EstadoPedido;
import com.api.cavoshbackend.pedido.enums.MetodoPago;
import com.api.cavoshbackend.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_recojo")
    private Instant fechaRecojo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoPedido estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, length = 30)
    private MetodoPago metodoPago;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    public Pedido(Usuario usuario, Local local, Instant fechaRecojo, MetodoPago metodoPago, BigDecimal total) {
        this.usuario = usuario;
        this.local = local;
        this.fechaRecojo = fechaRecojo;
        this.estado = EstadoPedido.RECIBIDO;
        this.metodoPago = metodoPago;
        this.total = total;
    }
}
