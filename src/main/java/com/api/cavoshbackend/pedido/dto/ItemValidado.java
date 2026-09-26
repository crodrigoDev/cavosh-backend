package com.api.cavoshbackend.pedido.dto;

import com.api.cavoshbackend.pedido.dto.request.ItemPedidoRequest;
import com.api.cavoshbackend.producto.model.OpcionPersonalizacion;
import com.api.cavoshbackend.producto.model.Producto;
import com.api.cavoshbackend.producto.model.ProductoSize;

import java.math.BigDecimal;

public record ItemValidado(
        ItemPedidoRequest request,
        Producto producto,
        ProductoSize size,
        OpcionPersonalizacion milk,
        OpcionPersonalizacion cream,
        OpcionPersonalizacion caffeine,
        BigDecimal precioUnitario
) {
}
