package com.api.cavoshbackend.producto.dto.response;

import com.api.cavoshbackend.producto.enums.TipoPersonalizacion;

import java.math.BigDecimal;

public record OpcionPersonalizacionResponse(
        Long id,
        TipoPersonalizacion tipo,
        String nombre,
        BigDecimal precioExtra,
        boolean predeterminada
) {
}
