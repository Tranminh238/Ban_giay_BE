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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CustomerProductRepository customerProductRepository;

    @Transactional
    public void createProduct(ProductCreateForm form) {
            Product product = Product.builder()
                    .name(form.getName())
                    .price(form.getPrice())
                    .size(form.getSize())
                    .sold(0)
                    .discount(form.getDiscount())
                    .quantity(form.getQuantity())
                    .brand(form.getBrand())
                    .description(form.getDescription())
                    .createdAt(LocalDateTime.now())
                    .status(1)
                    .build();

            productRepository.save(product);
    }


    @Transactional
    public void updateProduct(ProductCreateForm form) throws Exception {
            Product product = productRepository.findProductActive(form.getProductId())
                    .orElseThrow(() -> new Exception("Không tìm thấy sản phẩm"));

            product.setName(form.getName());
            product.setPrice(form.getPrice());
            product.setSize(form.getSize());
            product.setDiscount(form.getDiscount());
            product.setBrand(form.getBrand());
            product.setQuantity(form.getQuantity());
            product.setDescription(form.getDescription());
            product.setStatus(1);

            productRepository.save(product);

    }

    @Transactional
    public void deleteProduct(Long productId) throws Exception {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new Exception("Không tìm thấy sản phẩm"));
            product.setStatus(2);
            productRepository.save(product);
    }

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

//    public Page<ProductResponse> searchProduct(ProductSearchRequest req) throws Exception {
//        return customerProductRepository.getSearchProduct(req);
//    }

    public ProductResponse findProductDetailByProductId(Long productId) throws Exception {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Không tìm thấy sản phẩm" + productId));
        return convertToResponse(product);
    }


    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .size(product.getSize())
                .sold(product.getSold())
                .quantity(product.getQuantity())
                .discount(product.getDiscount())
                .brand(product.getBrand())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
