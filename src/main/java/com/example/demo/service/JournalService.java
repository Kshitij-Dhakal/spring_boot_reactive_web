package com.example.demo.service;

import com.example.demo.api.model.JournalModel;
import com.example.demo.entity.Journal;
import com.example.demo.entity.User;
import com.example.demo.repo.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;

    public Mono<?> save(User user, JournalModel journalModel) {
        Journal journal = journalModel.toJournal();
        return journalRepo.save(journal);
    }
}
