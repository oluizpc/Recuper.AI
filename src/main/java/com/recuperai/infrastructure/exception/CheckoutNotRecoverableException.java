package com.recuperai.infrastructure.exception;

public class CheckoutNotRecoverableException extends InvalidOperationException{ 
    public CheckoutNotRecoverableException (String message) {
        super(message);
    }
}
