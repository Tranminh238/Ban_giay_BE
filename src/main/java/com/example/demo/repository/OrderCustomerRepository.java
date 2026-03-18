package com.example.demo.repository;

import com.example.demo.dto.order.response.OrderDetail;
import com.example.demo.dto.order.response.OrderSearchAdmin;
import com.example.demo.dto.order.response.OrderSearchClient;
import org.springframework.data.domain.Page;

public interface OrderCustomerRepository {
    Page<OrderSearchClient> getAllOrderByClient(Long userId, Integer orderStatus, Integer page, Integer size);

    Page<OrderSearchAdmin> getAllOrderByAdmin(Integer orderStatus, String fromDate, String search, String toDate, Integer page, Integer size);

    OrderDetail getOrderDetailByOrderId(Long orderId);
}
