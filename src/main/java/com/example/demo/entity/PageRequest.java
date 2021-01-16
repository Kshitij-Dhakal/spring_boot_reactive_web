package com.example.demo.entity;

import com.example.demo.enums.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PageRequest {

    private long pageSize = 10; //per page
    private long pageNumber = 0; //page number
    private Order order = Order.DESC;
    private String orderBy = "";
    private String query = "";

    public static PageRequestBuilder builder() {
        return new PageRequest().toBuilder();
    }
}
