package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String profilePicture;
    private Long created;
    private Long updated;

    private List<Journal> journalList;

    public User(User user) {
        this.id = user.id;
        this.fullName = user.fullName;
        this.email = user.email;
        this.password = user.password;
        this.profilePicture = user.profilePicture;
        this.created = user.created;
        this.updated = user.updated;
        this.journalList = user.getJournalList();
    }
}
