package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private Integer price;
    private Integer sold;
    private Integer discount;
    private String brand;
    private String description;
    private Integer status;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
