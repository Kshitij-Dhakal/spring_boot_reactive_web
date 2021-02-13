package com.example.demo.core.utility;

import java.util.UUID;

public class Utility {
    private Utility() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
