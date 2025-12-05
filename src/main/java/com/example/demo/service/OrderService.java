package com.example.demo.service;

import com.example.demo.dto.order.request.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Long createOrder(OrderRequest req) {
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (product.getQuantity() < req.getQuantity()) {
            throw new RuntimeException("Không đủ số lượng sản phẩm");
        }
        if(product.getQuantity() == req.getQuantity()){
            product.setStatus(0);
        }

        long totalPrice = ((long) product.getPrice() * product.getDiscount() * req.getQuantity());

        Order order = Order.builder()
                .productId(req.getProductId())
                .clientId(req.getClientId())
                .quantity(req.getQuantity())
                .price(totalPrice)
                .status("pending")
                .consigneeName(req.getConsigneeName())
                .consigneePhoneNumber(req.getConsigneePhoneNumber())
                .deliveryAddress(req.getDeliveryAddress())
                .createAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);
        product.setQuantity(product.getQuantity() - req.getQuantity());
        product.setSold(product.getSold() + req.getQuantity());
        productRepository.save(product);
        return order.getId();
    }

    public void payment(boolean isPaid, Long orderId) throws Exception {

        if (isPaid) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new Exception("không tìm thấy Order với ID: " + orderId));
            order.setStatus("paid");
            order.setCompletedAt(LocalDateTime.now());
            orderRepository.save(order);
        } else {
            throw new Exception("Chưa thanh toán thành công");
        }
    }

}
