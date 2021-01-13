package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.example.demo.utility.Utility.uuid;

@Table("person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Persistable<String> {
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

    @Override
    public boolean isNew() {
        if (this.id == null) {
            this.id = uuid();
            return true;
        }
        return false;
    }
}
