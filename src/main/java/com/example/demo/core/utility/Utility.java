package com.example.demo.core.utility;

import java.time.LocalDateTime;
import java.util.UUID;

public class Utility {
    private Utility() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static Long nanos() {
        return System.nanoTime();
    }

    public static LocalDateTime timestamp() {
        return LocalDateTime.now();
    }
}
