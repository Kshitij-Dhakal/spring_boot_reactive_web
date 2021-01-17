package com.example.demo.api.controller;

import com.example.demo.api.model.LoginRequest;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public Mono<ResponseEntity<?>> login(@Valid @RequestBody final Mono<LoginRequest> authRequest) {
        return authService.login(authRequest)
                .map(jwt -> {
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                            var tokenBody = Map.of("token", jwt, "refreshToken", "");
                            return new ResponseEntity<Map<String, String>>(tokenBody, httpHeaders, HttpStatus.OK);
                        }
                );
    }

    @PostMapping("/refresh")
    public Mono<?> refreshAccessToken(@RequestHeader("refreshToken") final String refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }
}
