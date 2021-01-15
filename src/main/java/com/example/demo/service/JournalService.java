package com.example.demo.service;

import com.example.demo.api.model.JournalModel;
import com.example.demo.entity.Journal;
import com.example.demo.entity.User;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.repo.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.demo.utility.Lang.isBlank;
import static com.example.demo.utility.Lang.sanitizeDescription;
import static com.example.demo.utility.Utility.nanos;
import static com.example.demo.utility.Utility.uuid;

@Service
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;

    public Mono<?> save(User user, JournalModel journalModel) {
        Journal journal = journalModel.toJournal();
        String content = sanitizeDescription(journal.getContent());
        if (isBlank(content)) {
            throw new InvalidRequestException("Invalid content.");
        }
        Journal build = Journal.builder()
                .id(uuid())
                .content(content)
                .created(nanos())
                .updated(nanos())
                .build();
        return journalRepo.save(user, build);
    }

    public Flux<?> getMyJournal(User user) {
        return journalRepo.findByUser(user);
    }
}
