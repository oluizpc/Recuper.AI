package com.recuperai.infrastructure.exception;

public class MessageNotFoundException extends NotFoundException{
    public MessageNotFoundException (String message) {
        super(message);
    }
}
