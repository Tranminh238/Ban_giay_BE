package com.example.demo.dto.order.request;

import lombok.Data;

@Data
public class OrderSearchRequest {
    private Long clientId;
    private String status;
    private Integer page = 0;
    private Integer size = 10;
}
