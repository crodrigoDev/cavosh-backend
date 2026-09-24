package com.api.cavoshbackend.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerificarCodigoRequest(

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email es inválido")
        String email,

        @NotBlank(message = "El código es obligatorio")
        @Pattern(
                regexp = "\\d{6}",
                message = "El código debe tener 6 dígitos"
        )
        String codigo
) {
}
