package com.api.cavoshbackend.usuario.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dto para la request del endpoint de Registrar
 *
 * @param nombreCompleto nombre completo del usuario
 * @param email email del usuario
 * @param password contraseña del usuario
 * @param passwordConfirm confirmación de la contraseña del usuario
 */
public record RegistrarRequest(

        @NotBlank(message = "El nombre completo no puede estar en blanco")
        @Size(max = 150, message = "El nombre completo no debe exceder los 150 caracteres")
        String nombreCompleto,

        @NotBlank(message = "El email no puede estar en blanco")
        @Email(message = "El email tiene un formato inválido")
        String email,

        @NotBlank(message = "La contraseña no puede estar en blanco")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotBlank(message = "La contraseña de confirmación no puede estar en blanco")
        @Size(min = 8, message = "La contraseña de confirmación debe tener al menos 8 caracteres")
        String passwordConfirm
) {

    /**
     * Método para determinar si las contraseñas coinciden
     *
     * @return {@code true} si es que las contraseñas coinciden
     */
    @AssertTrue(message = "Las contraseñas no coinciden")
    public boolean isPasswordConfirmValido(){
        return password != null && password.equals(passwordConfirm);
    }
}
