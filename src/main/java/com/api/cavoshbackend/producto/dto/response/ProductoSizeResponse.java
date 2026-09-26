package com.api.cavoshbackend.producto.dto.response;

import com.api.cavoshbackend.producto.enums.Size;

import java.math.BigDecimal;

public record ProductoSizeResponse(Long id, Size nombre, BigDecimal precio, boolean predeterminado) {
}
