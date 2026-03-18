package com.example.demo.dto.order.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDetail {
    private Long orderId;
    private Long productId;
    private Long clientId;
    private Integer quantity;
    private Long price;
    private Long total;
    private Integer status;
    private String consigneeName;
    private String consigneePhoneNumber;
    private String deliveryAddress;
    private LocalDateTime createAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;
    private List<String> images;

}
