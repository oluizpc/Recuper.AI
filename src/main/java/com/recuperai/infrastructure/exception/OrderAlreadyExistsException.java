package com.recuperai.infrastructure.exception;

public class OrderAlreadyExistsException extends AlreadyExistsException{
    public OrderAlreadyExistsException (String message) {
        super(message);
    }

}
