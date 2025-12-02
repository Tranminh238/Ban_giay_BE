package com.example.demo.repository;

import com.example.demo.dto.order.request.OrderSearchRequest;
import com.example.demo.dto.order.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderCustomerRepository {
    Page<OrderResponse> searchOrders(OrderSearchRequest request) throws Exception;
}
