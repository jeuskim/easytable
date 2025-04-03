package com.example.easytable.exception;

public abstract class EasyTableException extends RuntimeException {

    public EasyTableException(String message) {
        super(message);
    }

    public EasyTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getStatusCode();
}
