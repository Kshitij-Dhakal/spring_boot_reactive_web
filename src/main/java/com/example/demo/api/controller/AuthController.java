package com.example.demo.api.controller;

import com.example.demo.api.model.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
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
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/token")
    public Mono<ResponseEntity<Map<String, ?>>> login(@Valid @RequestBody final Mono<LoginRequest> authRequest) {
        return authService.login(authRequest)
                .map(pair -> {
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + pair.getLeft());
                            var tokenBody = Map.of(ACCESS_TOKEN, pair.getLeft(),
                                    REFRESH_TOKEN, pair.getRight());
                            return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                        }
                );
    }

    @GetMapping("/profile")
    public Mono<User> getUserProfile() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(o -> {
                    User u = (User) o;
                    String id = u.getId();
                    return userService.findById(id);
                });
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<Map<String, ?>>> refreshAccessToken(@RequestHeader("refreshToken") String refreshToken,
                                                                   @RequestHeader("Authorization") String bearerToken) {
        return authService.refreshAccessToken(refreshToken, bearerToken)
                .map(s -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + s);
                    var tokenBody = Map.of(ACCESS_TOKEN, s);
                    return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                });
    }

    @DeleteMapping("/logout")
    public Mono<ResponseEntity<Map<String, ?>>> logout(@RequestParam("refreshToken") String refreshToken) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(o -> {
                    User user = (User) o;
                    return authService.logout(user.getId(), refreshToken);
                })
                .map(aBoolean -> {
                    var msg =
                            Map.of("success", true,
                                    "message", "logged out");
                    return new ResponseEntity<>(msg, HttpStatus.OK);
                });
    }
}
