package com.example.demo.core.repo;

import com.example.demo.entity.PageRequest;
import io.r2dbc.spi.Row;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SqlRepo {
    @NotNull
    public static Function<Row, Long> count() {
        return row -> {
            Object o = row.get("count");
            if (o == null) {
                return 0L;
            }
            return (Long) o;
        };
    }

    @NotNull
    public static String getQuery(PageRequest pageRequest, String query) {
        return String.format(query, pageRequest.getOrder().name());
    }
}
