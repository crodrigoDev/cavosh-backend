package com.api.cavoshbackend.pedido.dto.response;

import com.api.cavoshbackend.local.dto.response.LocalResponse;
import com.api.cavoshbackend.pedido.enums.EstadoPedido;
import com.api.cavoshbackend.pedido.enums.MetodoPago;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PedidoResponse(
        Long id, LocalResponse local, Instant fechaCreacion, Instant fechaRecojo,
        EstadoPedido estado, MetodoPago metodoPago, BigDecimal total, List<ItemPedidoResponse> items
) {
}
