package com.recuperai.infrastructure.exception;

public class CheckoutAlreadyExistsException extends AlreadyExistsException{
    public CheckoutAlreadyExistsException (String message) {
        super(message);
    }
}
