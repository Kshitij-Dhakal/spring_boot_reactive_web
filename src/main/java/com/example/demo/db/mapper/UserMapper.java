package com.example.demo.db.mapper;

import com.example.demo.core.type.RowMapper;
import com.example.demo.entity.User;
import io.r2dbc.spi.Row;

public class UserMapper implements RowMapper<User> {
    public User map(Row row) {
        return User.builder()
                .id((String) row.get("id"))
                .fullName((String) row.get("full_name"))
                .email((String) row.get("email"))
                .password((String) row.get("password"))
                .created((Long) row.get("created"))
                .updated((Long) row.get("updated"))
                .build();
    }
}
