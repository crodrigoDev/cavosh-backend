package com.api.cavoshbackend.pedido.dto.request;

import com.api.cavoshbackend.pedido.enums.MetodoPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.List;

public record CrearPedidoRequest(
        @NotNull @Positive Long localId,
        @Future Instant fechaRecojo,
        @NotNull MetodoPago metodoPago,
        @NotEmpty List<@NotNull @Valid ItemPedidoRequest> items
) {
}
