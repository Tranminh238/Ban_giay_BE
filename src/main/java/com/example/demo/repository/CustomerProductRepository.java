package com.example.demo.repository;

import com.example.demo.dto.product.request.ProductSearchRequest;
import com.example.demo.dto.product.response.ProductResponse;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProductRepository{
    Page<ProductResponse> getSearchProduct(ProductSearchRequest request) throws Exception;
}
