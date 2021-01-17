package com.example.demo.core.repo;

import com.example.demo.entity.PageRequest;
import io.r2dbc.spi.Row;

import javax.validation.constraints.NotNull;
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
    public static String getOrderByQuery(PageRequest pageRequest, String query) {
        return String.format(query, pageRequest.getOrderBy(), pageRequest.getOrder().name());
    }
}
