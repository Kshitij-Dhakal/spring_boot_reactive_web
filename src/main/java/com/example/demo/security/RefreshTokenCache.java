package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenCache {
    private Set<String> tokenId;
    private String email;
    private String password;
}
