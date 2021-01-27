package com.example.demo.core.utility;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

public class Security {
    public static final Random random = new SecureRandom();

    private Security() {
    }

    public static String secureRandom(int max) {
        return RandomStringUtils.random(max);
    }
}
