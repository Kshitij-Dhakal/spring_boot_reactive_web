package com.example.demo.api.controller;

import com.example.demo.api.model.LoginRequest;
import com.example.demo.api.model.RefreshTokenRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
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
                .map(pair -> {
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + pair.getLeft());
                            var tokenBody = Map.of("token", pair.getLeft(),
                                    "refreshToken", pair.getRight());
                            return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                        }
                );
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<?>> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshAccessToken(refreshTokenRequest.getRefreshToken(),
                refreshTokenRequest.getAccessToken())
                .map(s -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + s);
                    var tokenBody = Map.of("token", s);
                    return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                });
    }

    @DeleteMapping("/logout")
    public Mono<ResponseEntity<?>> logout(@RequestBody RefreshTokenRequest logoutRequest) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(o -> {
                    User user = (User) o;
                    return authService.logout(user.getId(), logoutRequest.getRefreshToken());
                })
                .map(aBoolean -> {
                    var msg =
                            Map.of("success", true,
                                    "message", "logged out");
                    return new ResponseEntity<>(msg, HttpStatus.OK);
                });
    }
}
