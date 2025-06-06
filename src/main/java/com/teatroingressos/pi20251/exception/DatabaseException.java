package com.teatroingressos.pi20251.exception;

public class DatabaseException extends AppException {

    public DatabaseException(String mensagem) {
        super(mensagem);
    }

    public DatabaseException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
