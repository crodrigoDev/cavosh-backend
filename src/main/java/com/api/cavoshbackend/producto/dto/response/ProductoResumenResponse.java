package com.api.cavoshbackend.producto.dto.response;

import java.math.BigDecimal;

public record ProductoResumenResponse(
        Long id,
        Long categoriaId,
        String nombre,
        String imagenUrl,
        boolean nuevo,
        boolean frecuente,
        BigDecimal precio
) {
}
