package com.example.demo.repo;

import reactor.core.publisher.Mono;

public interface AuthRepo {
    void save(String key, String refreshToken);

    Mono<String> get(String key);
}
