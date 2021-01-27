package com.example.demo.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class RedisSetImpl {
    private final ReactiveStringRedisTemplate redisTemplate;
    private ReactiveSetOperations<String, String> reactiveSetOperations;

    @PostConstruct
    public void setup() {
        reactiveSetOperations = redisTemplate.opsForSet();
    }

    public Mono<Long> add(String key, String... value) {
        return reactiveSetOperations.add(key, value);
    }

    public Mono<Boolean> isMember(String key, String value) {
        return reactiveSetOperations.isMember(key, value);
    }

    public Mono<Long> remove(String key, Object... value) {
        return reactiveSetOperations.remove(key, value);
    }
}
