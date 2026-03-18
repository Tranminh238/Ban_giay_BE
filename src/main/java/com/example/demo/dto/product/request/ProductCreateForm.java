package com.example.demo.dto.product.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateForm {
    private Long productId;
    private String name;
    private Integer price;
    private Integer size;
    private Integer discount;
    private String brand;
    private Integer quantity;
    private Integer status;
    private String description;
    private LocalDateTime createdAt;
    private List<String> images;


}
