package com.example.demo.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeUtils {
    private TimeUtils() {
        //no instance
    }
    public static Long nanos() {
        Date date = new Date();
        return date.getTime();
    }

    public static LocalDateTime timestamp() {
        return LocalDateTime.now();
    }


    public static Instant instant() {
        return Instant.now();
    }
}
