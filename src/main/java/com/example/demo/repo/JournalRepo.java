package com.example.demo.repo;

import com.example.demo.entity.Journal;
import reactor.core.publisher.Mono;

public interface JournalRepo {
    Mono<Journal> save(Journal journal);
}
