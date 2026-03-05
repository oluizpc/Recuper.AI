package com.recuperai.infrastructure.exception;

public class InvalidCheckoutStatusException extends InvalidStatusTransitionException{ 
    public InvalidCheckoutStatusException (String message) {
        super(message);
    }
}
