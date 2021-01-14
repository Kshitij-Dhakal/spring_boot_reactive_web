package com.example.demo.repo;

import com.example.demo.entity.Journal;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class JournalRepoImpl implements JournalRepo {

    private final DatabaseClient client;

    @Override
    public Mono<Journal> save(Journal journal) {
        return Mono.just(journal);
    }
}
