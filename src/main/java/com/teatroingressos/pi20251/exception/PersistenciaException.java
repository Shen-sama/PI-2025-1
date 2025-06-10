package com.teatroingressos.pi20251.exception;

public class PersistenciaException extends Exception {
    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
