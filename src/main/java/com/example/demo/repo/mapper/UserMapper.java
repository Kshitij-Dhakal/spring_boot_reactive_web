package com.example.demo.repo.mapper;

import com.example.demo.entity.User;
import io.r2dbc.spi.Row;
import org.jetbrains.annotations.NotNull;

public class UserMapper {
    private UserMapper() {
    }

    @NotNull
    public static User map(Row row) {
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
