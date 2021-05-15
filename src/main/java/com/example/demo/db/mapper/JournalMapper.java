package com.example.demo.db.mapper;

import com.example.demo.core.type.RowMapper;
import com.example.demo.entity.Journal;
import io.r2dbc.spi.Row;

public class JournalMapper implements RowMapper<Journal> {
    public Journal map(Row row) {
        return Journal.builder()
                .id((String) row.get("id"))
                .content((String) row.get("content"))
                .created((Long) row.get("created"))
                .updated((Long) row.get("updated"))
                .userId((String) row.get("user_id"))
                .build();
    }
}
