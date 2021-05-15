package com.example.demo.core.repo;

import com.example.demo.entity.PageRequest;
import io.r2dbc.spi.Row;

import java.util.function.Function;

public abstract class AbstractSqlRepo {
    public Function<Row, Long> count() {
        return row -> {
            Object o = row.get("count");
            if (o == null) {
                return 0L;
            }
            return (Long) o;
        };
    }

    public String getOrderByQuery(PageRequest pageRequest, String query) {
        return String.format(query, pageRequest.getOrderBy(), pageRequest.getOrder().name());
    }
}
