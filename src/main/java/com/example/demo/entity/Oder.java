package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Oder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private LocalTime order_date;
    private String status;
}
