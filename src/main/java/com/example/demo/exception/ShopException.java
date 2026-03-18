package com.example.demo.exception;

public class ShopException extends RuntimeException{
    public ShopException(String message) {
        super(message);
    }

    public ShopException(Throwable cause) {
        super(cause);
    }

}
