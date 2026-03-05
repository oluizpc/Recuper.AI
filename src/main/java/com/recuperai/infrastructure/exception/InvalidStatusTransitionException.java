package com.recuperai.infrastructure.exception;

public class InvalidStatusTransitionException extends RecuperaiException{
    public InvalidStatusTransitionException (String message) {
        super(message, 422);
    }

}
