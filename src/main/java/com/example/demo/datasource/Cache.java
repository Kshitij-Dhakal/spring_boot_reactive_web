package com.example.demo.datasource;

public interface Cache<T> {
    void save(String key, T t);

    T get(String key);
}
