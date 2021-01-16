package com.example.demo.core.exceptions;

public class ControllerException extends RuntimeException {
    private final String message;

    public ControllerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
