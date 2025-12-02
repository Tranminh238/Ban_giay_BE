package com.example.demo.dto.order.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long productId;
    private Long clientId;
    private Integer quantity;
    private Long price;

    private String consigneeName;
    private String consigneePhoneNumber;
    private String deliveryAddress;

    private String status;

    private LocalDateTime createAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;
}
