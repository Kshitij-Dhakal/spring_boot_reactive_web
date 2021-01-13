package com.example.demo.exceptions;

public class InvalidRequestException extends ControllerException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
