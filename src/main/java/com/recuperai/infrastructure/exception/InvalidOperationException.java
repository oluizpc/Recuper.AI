package com.recuperai.infrastructure.exception;

public class InvalidOperationException extends RecuperaiException {
    public InvalidOperationException (String message) {
        super(message, 422);
    }

}
