package com.example.demo.core.exceptions;

public class JwtExpiredExpiration extends ControllerException{
    public JwtExpiredExpiration(String message) {
        super(message);
    }
}
