package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Column("created")
    private Long created;
    @Column("updated")
    private Long updated;
}
