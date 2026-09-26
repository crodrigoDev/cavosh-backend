package com.api.cavoshbackend.pedido.dto.response;

import com.api.cavoshbackend.pedido.enums.EstadoPedido;
import com.api.cavoshbackend.pedido.enums.MetodoPago;

import java.math.BigDecimal;
import java.time.Instant;

public record PedidoResumenResponse(
        Long id, Long localId, String nombreLocal, Instant fechaCreacion,
        Instant fechaRecojo, EstadoPedido estado, MetodoPago metodoPago, BigDecimal total
) {
}
