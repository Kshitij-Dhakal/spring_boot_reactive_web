package com.example.demo.db.repo;

import reactor.core.publisher.Mono;

public interface AuthRepo {
    Mono<Long> save(String accountId, String tokenId);

    Mono<Long> removeToken(String accountId, String token);

    Mono<Boolean> checkToken(String accountId, String tokenId);
}
