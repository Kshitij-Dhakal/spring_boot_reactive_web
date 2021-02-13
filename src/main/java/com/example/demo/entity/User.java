package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("person")
public class User {
    @Id
    @Column("id")
    private String id;
    @Column("full_name")
    private String fullName;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("profile_picture")
    private String profilePicture;
    @Column("created")
    private Long created;
    @Column("updated")
    private Long updated;

    @Singular("journal")
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
