package com.api.cavoshbackend.usuario.exception;

public class EmailYaEstaRegistradoException extends RuntimeException {
    public EmailYaEstaRegistradoException() {
        super("El email ya esta registrado");
    }
}
