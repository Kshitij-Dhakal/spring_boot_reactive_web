package com.example.demo.exceptions;

public class JwtExpiredExpiration extends ControllerException{
    public JwtExpiredExpiration(String message) {
        super(message);
    }
}
