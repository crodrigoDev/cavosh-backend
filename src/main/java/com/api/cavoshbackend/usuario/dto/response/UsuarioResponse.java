package com.api.cavoshbackend.usuario.dto.response;

public record UsuarioResponse(

        String id,
        String nombreCompleto,
        String email,
        String fotoUrl
) {
}
