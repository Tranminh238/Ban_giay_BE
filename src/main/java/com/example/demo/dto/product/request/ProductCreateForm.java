package com.example.demo.dto.product.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
public class ProductCreateForm {
    private Long productId;
    private String name;
    private Integer price;
    private Integer discount;
    private String brand;
    private Integer quantity;
    private Integer status;
    private String description;

    private LocalDateTime createdAt;

}
