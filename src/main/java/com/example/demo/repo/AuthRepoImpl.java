package com.example.demo.repo;

import com.example.demo.datasource.RedisSetImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AuthRepoImpl implements AuthRepo {
    private final RedisSetImpl redisSet;

    @Override
    public Mono<Long> save(String accountId, String tokenId) {
        return redisSet.add(accountId, tokenId);
    }

    @Override
    public Mono<Long> removeToken(String accountId, String token) {
        return redisSet.remove(accountId, token);
    }

    @Override
    public Mono<Boolean> checkToken(String accountId, String tokenId) {
        return redisSet.isMember(accountId, tokenId);
    }
}
