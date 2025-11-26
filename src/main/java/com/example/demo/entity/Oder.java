package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Oder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long productId;
    private Long clientId;
    private LocalTime order_date;
    private String status;
    private String deliveryAddress;
    private LocalDateTime createAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;
}
