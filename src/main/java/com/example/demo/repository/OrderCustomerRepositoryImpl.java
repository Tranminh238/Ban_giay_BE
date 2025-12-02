package com.example.demo.repository;

import com.example.demo.dto.order.request.OrderSearchRequest;
import com.example.demo.dto.order.response.OrderResponse;
import com.example.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderCustomerRepositoryImpl implements OrderCustomerRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Page<OrderResponse> searchOrders(OrderSearchRequest request) throws Exception {

        String sql =
                """
                SELECT 
                    o.id,
                    o.product_id,
                    o.client_id,
                    o.quantity,
                    o.price,
                    o.status,
                    o.consignee_name,
                    o.consignee_phone_number,
                    o.delivery_address,
                    o.create_at,
                    o.canceled_at,
                    o.completed_at
                FROM orders o
                WHERE 1 = 1
                """;

        Map<String, Object> params = new HashMap<>();

        if (request.getClientId() != null) {
            sql += " AND o.client_id = :clientId ";
            params.put("clientId", request.getClientId());
        }

        if (StringUtils.isNotBlank(request.getStatus())) {
            sql += " AND o.status = :status ";
            params.put("status", request.getStatus());
        }

        sql += " ORDER BY o.create_at DESC ";

        List<OrderResponse> list = jdbc.query(sql, params, (rs, rowNum) ->
                OrderResponse.builder()
                        .id(rs.getLong("id"))
                        .productId(rs.getLong("product_id"))
                        .clientId(rs.getLong("client_id"))
                        .quantity(rs.getInt("quantity"))
                        .price(rs.getLong("price"))
                        .status(rs.getString("status"))
                        .consigneeName(rs.getString("consignee_name"))
                        .consigneePhoneNumber(rs.getString("consignee_phone_number"))
                        .deliveryAddress(rs.getString("delivery_address"))
                        .createAt(rs.getTimestamp("create_at") != null ?
                                rs.getTimestamp("create_at").toLocalDateTime() : null)
                        .canceledAt(rs.getTimestamp("canceled_at") != null ?
                                rs.getTimestamp("canceled_at").toLocalDateTime() : null)
                        .completedAt(rs.getTimestamp("completed_at") != null ?
                                rs.getTimestamp("completed_at").toLocalDateTime() : null)
                        .build()
        );

        return PageUtil.getPage(list, request.getPage(), request.getSize());
    }
}
