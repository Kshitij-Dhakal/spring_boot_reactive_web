package com.example.demo.core.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeUtility {

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
