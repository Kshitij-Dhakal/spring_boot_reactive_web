package com.example.demo.core.util;

public class StringUtils {
    private StringUtils() {
        //no instance
    }

    public static String sanitizeText(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.trim();
    }

    public static String sanitizeDescription(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.trim();
    }

    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
