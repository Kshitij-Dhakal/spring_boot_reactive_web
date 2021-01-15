package com.example.demo.repo;

import com.example.demo.entity.Journal;
import com.example.demo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JournalRepo {
    Mono<Journal> save(User user, Journal journal);

    Mono<Journal> findById(String id);

    Flux<Journal> findByUser(User user);
}
