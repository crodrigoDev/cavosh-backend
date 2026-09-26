package com.api.cavoshbackend.usuario.exception;

public class CuentaNoVerificadaException extends RuntimeException {

    public CuentaNoVerificadaException() {
        super("La cuenta no esta verificada");
    }
}
