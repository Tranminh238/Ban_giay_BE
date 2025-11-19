package com.example.demo.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequest {
    private String name;
    private int price;
    private int stock;
    private int sold;
    private int discount;
    private String brand;
    private List<Integer> categoryId;
}
