package com.example.demo.repo.mapper;

import com.example.demo.entity.Journal;
import io.r2dbc.spi.Row;

public class JournalMapper {
    private JournalMapper() {
    }

    public static Journal map(Row row) {
        return Journal.builder()
                .id((String) row.get("id"))
                .content((String) row.get("content"))
                .created((Long) row.get("created"))
                .updated((Long) row.get("updated"))
                .userId((String) row.get("user_id"))
                .build();
    }
}
