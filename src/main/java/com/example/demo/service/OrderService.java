package com.example.demo.service;

import com.example.demo.dto.order.request.OrderRequest;
import com.example.demo.dto.order.request.OrderSearchRequest;
import com.example.demo.dto.order.response.OrderResponse;
import com.example.demo.entity.Oder;
import com.example.demo.repository.OrderCustomerRepository;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCustomerRepository customerRepo;

    public void createOrder(OrderRequest req) {
        Oder order = Oder.builder()
                .productId(req.getProductId())
                .clientId(req.getClientId())
                .quantity(req.getQuantity())
                .price(req.getPrice())
                .status("pending")
                .consigneeName(req.getConsigneeName())
                .consigneePhoneNumber(req.getConsigneePhoneNumber())
                .deliveryAddress(req.getDeliveryAddress())
                .createAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);
    }


}
