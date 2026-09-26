package com.api.cavoshbackend.local.dto.response;

public record LocalResponse(
        Long id,
        String nombre,
        String direccion,
        String horario,
        boolean frecuente,
        Long distritoId,
        String distritoNombre
) {
}
