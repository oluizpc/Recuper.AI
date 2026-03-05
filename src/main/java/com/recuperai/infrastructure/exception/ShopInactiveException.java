package com.recuperai.infrastructure.exception;

public class ShopInactiveException extends InactiveEntityException {
    public ShopInactiveException (String message) {
        super(message);
    }
}

