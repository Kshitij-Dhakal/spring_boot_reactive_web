package com.example.demo.repo;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class AuthRepoImpl implements AuthRepo {
    Map<String, String> blackList = Maps.newHashMap();

    @Override
    public void save(String key, String refreshToken) {
    }

    @Override
    public Mono<String> get(String key) {
        return null;
    }
}
