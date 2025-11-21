package com.example.demo.controller;

import com.example.demo.dto.base.BaseResponse;
import com.example.demo.dto.product.request.ProductCreateForm;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("create-product")
    public ResponseEntity<BaseResponse> createProduct(@RequestBody @Valid ProductCreateForm productCreateForm){
        try{
            productService.createProduct(productCreateForm);
            return ResponseEntity.ok(new BaseResponse(200, "Success", null));
        }
        catch (Exception e){
            return  ResponseEntity.ok(new BaseResponse(500, "Success", null));
        }
    }
    @PostMapping("update-product")
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody @Valid ProductCreateForm productCreateForm){
        try{
            productService.updateProduct(productCreateForm);
            return ResponseEntity.ok(new BaseResponse(200, "Success", null));
        }
        catch (Exception e){
            return  ResponseEntity.ok(new BaseResponse(500, "Success", null));
        }
    }
}
