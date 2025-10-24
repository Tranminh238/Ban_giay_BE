package com.example.demo.exception;

public class Exception extends RuntimeException{
    public Exception(String message) {
        super(message);
    }

    public Exception(Throwable cause) {
        super(cause);
    }
}
