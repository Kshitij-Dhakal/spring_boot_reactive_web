package com.example.demo.datasource;

import com.example.demo.entity.Journal;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JournalCacheImpl implements Cache<Journal> {
    Map<String, Journal> journalMap = Maps.newHashMap();

    @Override
    public void save(String key, Journal journal) {
        journalMap.put(key, journal);
    }

    @Override
    public Journal get(String key) {
        return journalMap.get(key);
    }
}
