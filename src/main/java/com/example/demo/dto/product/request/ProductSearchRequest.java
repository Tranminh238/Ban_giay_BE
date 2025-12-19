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
    private Integer price;
    private Integer sizes;
    private Integer sold;
    private Integer discount;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private Integer status;
    private Integer page = 0;
    private Integer size = 10;
    private List<Integer> brandlist;
    private String sortBy = "productId";
    private String sortDirection = "DESC";
}
