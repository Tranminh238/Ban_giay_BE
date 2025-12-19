package com.example.demo.controller;

import com.example.demo.dto.base.BaseResponse;
import com.example.demo.dto.product.request.ProductCreateForm;
import com.example.demo.dto.product.request.ProductSearchRequest;
import com.example.demo.dto.product.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("create")
    public ResponseEntity<BaseResponse> createProduct(@RequestBody @Valid ProductCreateForm productCreateForm){
        try{
            productService.createProduct(productCreateForm);
            return ResponseEntity.ok(new BaseResponse(200, "Success", null));
        }
        catch (Exception e){
            return  ResponseEntity.ok(new BaseResponse(500, "Fail" + e.getMessage(), null));
        }
    }

    @PutMapping("update")
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody @Valid ProductCreateForm productCreateForm){
        try{
            productService.updateProduct(productCreateForm);
            return ResponseEntity.ok(new BaseResponse(200, "Success", null));
        }
        catch (Exception e){
            return  ResponseEntity.ok(new BaseResponse(500, "Fail", null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new BaseResponse(200, "Xóa sản phẩm thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(500, "Fail: " + e.getMessage(), null));
        }
    }
    @PostMapping("/search")
    public ResponseEntity<BaseResponse> searchProducts(@RequestBody ProductSearchRequest request) {
        return ResponseEntity.ok(productService.searchProducts(request));
    }
//    @GetMapping("/search")
//    public ResponseEntity<?> search(ProductSearchRequest req) throws Exception {
//
//        Page<ProductResponse> page = productService.searchProduct(req);
//
//        return ResponseEntity.ok(
//                Map.of(
//                        "currentPage", page.getNumber(),
//                        "pageSize", page.getSize(),
//                        "totalItems", page.getTotalElements(),
//                        "totalPages", page.getTotalPages(),
//                        "hasNext", page.hasNext(),
//                        "hasPrevious", page.hasPrevious(),
//                        "items", page.getContent()
//                )
//        );
//    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BaseResponse> getProductDetail(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    new BaseResponse(200, "Lấy sản phẩm thành công", productService.findProductDetailByProductId(id))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(404, "Không tìm thấy: " + e.getMessage(), null));
        }
    }
}
