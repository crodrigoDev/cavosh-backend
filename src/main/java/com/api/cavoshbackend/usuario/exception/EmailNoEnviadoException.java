package com.api.cavoshbackend.usuario.exception;

public class EmailNoEnviadoException extends RuntimeException {

    public EmailNoEnviadoException(Throwable cause){
        super("No se pudo enviar el correo", cause);
    }

    public EmailNoEnviadoException() {
        super("No se pudo enviar el correo");
    }
}
