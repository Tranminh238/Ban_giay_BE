package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private String description;
}
