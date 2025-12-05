package com.example.demo.dto.order.request;

import lombok.Data;

@Data
public class OrderRequest {
    private Long productId;
    private Long clientId;
    private Integer quantity;
    private Long price;
    private String consigneeName;
    private String consigneePhoneNumber;
    private String deliveryAddress;
}
