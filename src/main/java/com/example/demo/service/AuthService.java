package com.example.demo.service;

import com.example.demo.api.model.LoginRequest;
import com.example.demo.repo.AuthRepo;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;
    private final AuthRepo authCache;

    @NotNull
    public Mono<String> login(Mono<LoginRequest> authRequest) {
        return authRequest
                .flatMap(login -> authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()))
                        .map(tokenProvider::createToken)
                );
    }

    public Mono<String> refreshAccessToken(String refreshToken) {
        //todo implementation
        return Mono.just("");
    }
}