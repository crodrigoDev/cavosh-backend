package com.api.cavoshbackend.pedido.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @NotNull @Positive Long productoId,
        @NotNull @Positive Long productoSizeId,
        @Positive Long milkId,
        @Positive Long creamId,
        @Positive Long caffeineId,
        @NotNull @Positive Integer cantidad
) {
}
