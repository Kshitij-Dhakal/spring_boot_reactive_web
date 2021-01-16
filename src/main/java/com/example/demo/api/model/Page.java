package com.example.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Page<T> {
    private long pageSize;
    private long pageNumber;
    private long total;
    private List<T> data;

    public static <T> List<Object> map(List<T> tList) {
        return tList.stream().map(t -> (Object) t).collect(toList());
    }
}
