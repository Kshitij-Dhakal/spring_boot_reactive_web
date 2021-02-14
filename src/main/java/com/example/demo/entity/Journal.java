package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("journal")
public class Journal {
    @Id
    @Column("id")
    private String id;
    @Column("content")
    private String content;
    @Column("created")
    private Long created;
    @Column("updated")
    private Long updated;
    @JsonIgnore
    private String userId;
}
