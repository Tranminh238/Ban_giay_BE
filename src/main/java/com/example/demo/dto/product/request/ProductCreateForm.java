package com.example.demo.dto.product.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
public class ProductCreateForm {
    private Long productId;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer discount;
    @NotNull
    private String brand;

    private Integer status;
    @NotNull
    private String description;
//    @NotNull
//    private LocalDateTime createdAt;
//    @NotNull
//    private LocalDateTime updateAt;
}
