package com.example.demo.repo;

import com.example.demo.api.model.Page;
import com.example.demo.entity.Journal;
import com.example.demo.entity.PageRequest;
import com.example.demo.entity.User;
import reactor.core.publisher.Mono;

public interface JournalRepo {
    Mono<Journal> save(User user, Journal journal);

    Mono<Journal> update(Journal journal);

    Mono<Journal> findById(String id);

    Mono<Page<Journal>> findByUser(User user, PageRequest pageRequest);

    Mono<Boolean> delete(String id);
}
