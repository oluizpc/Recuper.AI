package com.recuperai.infrastructure.exception;

public class CustomerAlreadyExistsException extends AlreadyExistsException{
    public CustomerAlreadyExistsException (String message) {
        super(message);
    }
}
