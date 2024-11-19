package com.redesocial.exception;

public class AutenticacaoException extends RuntimeException{
    public AutenticacaoException(String message) {
        super(message);
    }

    public AutenticacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
