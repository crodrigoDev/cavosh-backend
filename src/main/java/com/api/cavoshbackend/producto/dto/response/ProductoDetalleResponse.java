package com.api.cavoshbackend.producto.dto.response;

import java.util.List;

public record ProductoDetalleResponse(
        Long id,
        Long categoriaId,
        String nombre,
        String descripcion,
        String imagenUrl,
        boolean nuevo,
        boolean frecuente,
        boolean personalizable,
        List<ProductoSizeResponse> tamanos,
        List<OpcionPersonalizacionResponse> opcionesPersonalizacion
) {
}
