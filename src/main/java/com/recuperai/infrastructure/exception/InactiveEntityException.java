package com.recuperai.infrastructure.exception;

public class InactiveEntityException extends RecuperaiException{
    public InactiveEntityException (String message) {
        super(message, 422);
    }
}
