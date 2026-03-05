package com.recuperai.infrastructure.exception;

public class CustomerNotFoundException extends NotFoundException{
    public CustomerNotFoundException (String message) {
        super(message);
    }
}
