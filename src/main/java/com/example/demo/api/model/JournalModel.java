package com.example.demo.api.model;

import com.example.demo.entity.Journal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalModel {
    private String id;
    private String content;
    private Long created;
    private Long updated;

    public JournalModel fromJournal(Journal journal) {
        return JournalModel.builder()
                .id(journal.getId())
                .content(journal.getContent())
                .created(journal.getCreated())
                .updated(journal.getUpdated())
                .build();
    }

    public Journal toJournal() {
        return Journal.builder()
                .id(id)
                .content(content)
                .created(created)
                .updated(updated)
                .build();
    }
}
