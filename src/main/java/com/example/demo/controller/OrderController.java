package com.example.demo.controller;

import com.example.demo.dto.order.request.OrderRequest;
import com.example.demo.dto.order.request.OrderSearchRequest;
import com.example.demo.dto.order.response.OrderResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderRequest request) {
        orderService.createOrder(request);
        return "Tạo đơn hàng thành công";
    }

}