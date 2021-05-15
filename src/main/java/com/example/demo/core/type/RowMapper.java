package com.example.demo.core.type;

import io.r2dbc.spi.Row;

import java.util.function.Function;

@FunctionalInterface
public interface RowMapper<T> extends Function<Row, T> {
    @Override
    default T apply(Row row) {
        return map(row);
    }

    T map(Row row);
}
