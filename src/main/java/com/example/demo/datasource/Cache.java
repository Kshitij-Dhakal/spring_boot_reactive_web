package com.example.demo.datasource;

import reactor.core.publisher.Mono;

public interface Cache<T> {
    void save(String key, T t);

    Mono<T> get(String key);
}
