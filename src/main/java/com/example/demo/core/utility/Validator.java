package com.example.demo.core.utility;

import org.apache.commons.validator.routines.EmailValidator;

public class Validator {
    private Validator() {
    }

    public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
