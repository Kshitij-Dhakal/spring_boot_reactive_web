package com.example.demo.core.util;

import org.apache.commons.validator.routines.EmailValidator;

public class Validator {
    private Validator() {
        //no instance
    }

    public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
