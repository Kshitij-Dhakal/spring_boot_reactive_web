package com.example.demo.core.util;

import java.util.UUID;

public class JournalUtils {
    private JournalUtils() {
        //no instance
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
