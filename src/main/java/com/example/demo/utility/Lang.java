package com.example.demo.utility;

import org.apache.commons.lang3.StringUtils;

public class Lang {
    private Lang() {
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
        return StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
