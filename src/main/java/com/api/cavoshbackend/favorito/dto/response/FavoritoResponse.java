package com.api.cavoshbackend.favorito.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record FavoritoResponse(
        Long id,
        Long productoId,
        String nombre,
        String imagenUrl,
        BigDecimal precio,
        boolean disponible,
        Instant fechaCreacion
) {
}
