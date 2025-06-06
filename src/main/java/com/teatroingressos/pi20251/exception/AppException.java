package com.teatroingressos.pi20251.exception;

public abstract class AppException extends RuntimeException {

    public AppException(String mensagem) {
        super(mensagem);
    }

    public AppException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
