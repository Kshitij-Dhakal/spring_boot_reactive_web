package com.example.demo.security.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:security.properties")
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.validity.in.millis:3600000}")
    private long validityInMs;
}
