package com.example.demo.api.controller;

import com.example.demo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

public abstract class AbstractController {
    protected <T> Mono<T> authorized(Function<User, Mono<? extends T>> f) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(o -> {
                    User user = (User) o;
                    return f.apply(user);
                });
    }

    protected ResponseEntity<Map<String, Boolean>> deleted() {
        return ResponseEntity.ok(Map.of("success", true));
    }
}
