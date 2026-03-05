package com.recuperai.infrastructure.exception;

public class NotFoundException extends RecuperaiException{
    public NotFoundException(String message) {
        super(message, 404);
    }

}
