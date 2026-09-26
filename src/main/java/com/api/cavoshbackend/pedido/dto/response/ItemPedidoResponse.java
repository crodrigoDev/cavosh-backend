package com.api.cavoshbackend.pedido.dto.response;

import com.api.cavoshbackend.producto.enums.Size;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id, Long productoId, String nombreProducto, Long productoSizeId, Size size,
        OpcionPedidoResponse milk, OpcionPedidoResponse cream, OpcionPedidoResponse caffeine,
        Integer cantidad, BigDecimal precioUnitario, BigDecimal total
) {
}
