package com.example.demo.core.util;

import org.apache.commons.lang3.RandomStringUtils;

public class SecurityUtils {
    private SecurityUtils() {
        //no instance
    }

    public static String secureRandom(int max) {
        return RandomStringUtils.randomAlphanumeric(max);
    }
}
