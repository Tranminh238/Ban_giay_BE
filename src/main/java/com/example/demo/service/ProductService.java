package com.example.demo.service;

import com.example.demo.dto.product.request.ProductCreateForm;
import com.example.demo.entity.Product;
import com.example.demo.enums.ProductEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    public Product createProduct(ProductCreateForm form){
        return Product.builder()
                .categoryId(form.getCategoryId())
                .name(form.getName())
                .brand(form.getBrand())
                .price(form.getPrice())
                .discount(form.getDiscount())
                .sold(0)
                .description(form.getDescription())
                .stock(form.getStock())
                .status(ProductEnums.Status.ACTIVE.getValue())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void updateProduct(Product product, ProductCreateForm form){
        product.setCategoryId(form.getCategoryId());
        product.setName(form.getName());
        product.setBrand(form.getBrand());
        product.setPrice(form.getPrice());
        product.setDiscount(form.getDiscount());
        product.setDescription(form.getDescription());
        product.setStock(form.getStock());
        product.setUpdatedAt(LocalDateTime.now());
    }
}
