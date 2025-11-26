package com.example.demo.service;

import com.example.demo.dto.base.BaseResponse;
import com.example.demo.dto.product.request.ProductCreateForm;
import com.example.demo.dto.product.request.ProductSearchRequest;
import com.example.demo.dto.product.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.repository.CustomerProductRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CustomerProductRepository customerProductRepository;

    @Transactional
    public BaseResponse createProduct(ProductCreateForm form) {
        try {

            Product product = Product.builder()
                    .name(form.getName())
                    .price(form.getPrice())
                    .sold(0)
                    .discount(form.getDiscount())
                    .brand(form.getBrand())
                    .description(form.getDescription())
                    .status(1)
                    .build();

            Product savedProduct = productRepository.save(product);

            ProductResponse response = convertToResponse(savedProduct);

            return new BaseResponse(
                    HttpStatus.CREATED.value(),
                    "Tạo sản phẩm thành công",
                    response
            );
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            return new BaseResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi khi tạo sản phẩm: " + e.getMessage(),
                    null
            );
        }
    }

    /**
     * Cập nhật sản phẩm
     */
    @Transactional
    public BaseResponse updateProduct(ProductCreateForm form) {
        try {
            Product product = productRepository.findProductActive(form.getProductId())
                    .orElseThrow(() -> new Exception("Không tìm thấy sản phẩm"));

            product.setName(form.getName());
            product.setPrice(form.getPrice());
            product.setDiscount(form.getDiscount());
            product.setBrand(form.getBrand());
            product.setDescription(form.getDescription());
            product.setStatus(1);

            Product updatedProduct = productRepository.save(product);

            ProductResponse response = convertToResponse(updatedProduct);

            return new BaseResponse(
                    HttpStatus.OK.value(),
                    "Cập nhật sản phẩm thành công",
                    response
            );
        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
            return new BaseResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi khi cập nhật sản phẩm: " + e.getMessage(),
                    null
            );
        }
    }

    /**
     * Xóa sản phẩm (soft delete)
     */
    @Transactional
    public BaseResponse deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            product.setStatus(0);
            productRepository.save(product);

            return new BaseResponse(
                    HttpStatus.OK.value(),
                    "Xóa sản phẩm thành công",
                    null
            );
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            return new BaseResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi khi xóa sản phẩm: " + e.getMessage(),
                    null
            );
        }
    }

    /**
     * Lấy chi tiết sản phẩm
     */
//    public BaseResponse getProductById(Long id) {
//        try {
//            Product product = productRepository.findActiveProductById(id)
//                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
//
//            ProductResponse response = convertToResponse(product);
//
//            return new BaseResponse(
//                    HttpStatus.OK.value(),
//                    "Lấy thông tin sản phẩm thành công",
//                    response
//            );
//        } catch (Exception e) {
//            log.error("Error getting product: {}", e.getMessage());
//            return new BaseResponse(
//                    HttpStatus.NOT_FOUND.value(),
//                    "Không tìm thấy sản phẩm",
//                    null
//            );
//        }
//    }
//
//    /**
//     * Lấy tất cả sản phẩm active
//     */
//    public BaseResponse getAllActiveProducts() {
//        try {
//            List<Product> products = productRepository.findByStatus(1);
//
//            List<ProductResponse> responses = products.stream()
//                    .map(this::convertToResponse)
//                    .collect(Collectors.toList());
//
//            return new BaseResponse(
//                    HttpStatus.OK.value(),
//                    "Lấy danh sách sản phẩm thành công",
//                    responses
//            );
//        } catch (Exception e) {
//            log.error("Error getting all products: {}", e.getMessage());
//            return new BaseResponse(
//                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    "Lỗi khi lấy danh sách sản phẩm",
//                    null
//            );
//        }
//    }

    /**
     * Tìm kiếm sản phẩm với pagination
     */
    public BaseResponse searchProducts(ProductSearchRequest request) {
        try {
            Page<ProductResponse> products = customerProductRepository.getSearchProduct(request);

            return new BaseResponse(
                    HttpStatus.OK.value(),
                    "Tìm kiếm sản phẩm thành công",
                    products
            );
        } catch (Exception e) {
            log.error("Error searching products: {}", e.getMessage());
            return new BaseResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi khi tìm kiếm sản phẩm",
                    null
            );
        }
    }

    /**
     * Convert Entity to Response DTO
     */
    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .sold(product.getSold())
                .discount(product.getDiscount())
                .brand(product.getBrand())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .build();
    }
}