package com.example.demo.exceptions;

public class DuplicateResourceException extends ControllerException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
