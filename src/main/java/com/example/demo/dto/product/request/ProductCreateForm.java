package com.example.demo.dto.product.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
public class ProductCreateForm {
    @NotNull
    private Long categoryId;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer discount;
    @NotNull
    private String brand;
    @NotNull
    private String description;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updateAt;
}
