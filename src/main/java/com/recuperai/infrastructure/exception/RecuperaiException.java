package com.recuperai.infrastructure.exception;

public abstract class RecuperaiException extends RuntimeException {
    private final int statusCode;
    public RecuperaiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode() { return statusCode; }
}

