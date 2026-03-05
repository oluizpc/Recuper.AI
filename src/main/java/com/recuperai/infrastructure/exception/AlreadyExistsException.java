package com.recuperai.infrastructure.exception;

public class AlreadyExistsException extends RecuperaiException{
    public AlreadyExistsException(String message) {
        super(message, 409);
    }
}
