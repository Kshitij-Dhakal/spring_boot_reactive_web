package com.example.demo.core.exceptions;

public class InvalidRequestException extends ControllerException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
