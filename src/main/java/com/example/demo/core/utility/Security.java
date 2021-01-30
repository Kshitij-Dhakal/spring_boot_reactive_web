package com.example.demo.core.utility;

import org.apache.commons.lang3.RandomStringUtils;

public class Security {

    private Security() {
    }

    public static String secureRandom(int max) {
        return RandomStringUtils.randomAlphanumeric(max);
    }
}
