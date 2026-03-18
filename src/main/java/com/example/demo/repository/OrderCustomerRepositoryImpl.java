package com.example.demo.repository;

import com.example.demo.dto.order.response.OrderDetail;
import com.example.demo.dto.order.response.OrderSearchAdmin;
import com.example.demo.dto.order.response.OrderSearchClient;
import com.example.demo.exception.ShopException;
import com.example.demo.util.PageUtil;
import com.example.demo.util.QueryUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderCustomerRepositoryImpl implements OrderCustomerRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final AssetRepository assetRepository;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<OrderSearchClient> getAllOrderByClient(Long userId, Integer orderStatus, Integer page, Integer size){
        try {
            List<OrderSearchClient> orderList = jdbc.query(
                    createGetAllOrderQueryByClientQuery(userId, orderStatus),
                    setParameterGetAllOrderByClient(userId, orderStatus),
                    (rs, rowNum) -> new OrderSearchClient(
                            rs.getLong("orderId"),
                            rs.getLong("price"),
                            rs.getInt("quantity"),
                            rs.getLong("total"),
                            rs.getString("productName"),
                            rs.getInt("status"),
                            StringUtils.isBlank(rs.getString("createAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("createAt"), dateTimeFormatter),
                            StringUtils.isBlank(rs.getString("canceledAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("canceledAt"), dateTimeFormatter),
                            StringUtils.isBlank(rs.getString("completedAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("completedAt"), dateTimeFormatter)
                    )
            );

            return PageUtil.getPage(orderList, page, size);
        } catch (Exception e) {
            throw new ShopException(e.getMessage());
        }
    }
    @Override
    public Page<OrderSearchAdmin> getAllOrderByAdmin(Integer orderStatus, String fromDate, String search, String toDate, Integer page, Integer size){
        try {
            List<OrderSearchAdmin> orderList = jdbc.query(
                    createQueryGetAllOrderByAdmin(orderStatus, fromDate, toDate, search),
                    setParameterGetAllOrderQueryByAdmin(orderStatus, fromDate, toDate, search),
                    (rs, rowNum) -> new OrderSearchAdmin(
                            rs.getLong("orderId"),
                            rs.getLong("price"),
                            rs.getInt("quantity"),
                            rs.getLong("total"),
                            rs.getString("productName"),
                            rs.getInt("status"),
                            StringUtils.isBlank(rs.getString("createAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("createAt"), dateTimeFormatter),
                            StringUtils.isBlank(rs.getString("canceledAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("canceledAt"), dateTimeFormatter),
                            StringUtils.isBlank(rs.getString("completedAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("completedAt"), dateTimeFormatter),
                            rs.getString("clientName")
                    )
            );

            return PageUtil.getPage(orderList, page, size);
        } catch (Exception e) {
            throw new ShopException(e.getMessage());
        }
    }
    @Override
    public OrderDetail getOrderDetailByOrderId(Long orderId) {

        try {
            OrderDetail orderDetail = jdbc.queryForObject(
                    createQueryGetOrderDetail(orderId),
                    setParameterGetOrderDetailByOrderId(orderId),
                    (rs, rowNum) -> OrderDetail.builder()
                            .orderId(rs.getLong("orderId"))
                            .productId(rs.getLong("productId"))
                            .clientId(rs.getLong("clientId"))
                            .status(rs.getInt("status"))
                            .quantity(rs.getInt("quantity"))
                            .price(rs.getLong("price"))
                            .total(rs.getLong("total"))
                            .consigneeName(rs.getString("consigneeName"))
                            .consigneePhoneNumber(rs.getString("consigneePhoneNumber"))
                            .deliveryAddress(rs.getString("deliveryAddress"))
                            .createAt(StringUtils.isBlank(rs.getString("createAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("createAt"), dateTimeFormatter))
                            .canceledAt(StringUtils.isBlank(rs.getString("canceledAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("canceledAt"), dateTimeFormatter))
                            .completedAt(StringUtils.isBlank(rs.getString("completedAt"))
                                    ? null
                                    : LocalDateTime.parse(rs.getString("completedAt"), dateTimeFormatter))

                            .build()
            );

            return orderDetail;
        } catch (Exception e) {
            throw new ShopException(e.getMessage());
        }
    }
    private String createQueryGetOrderDetail(Long orderId){
        String select = """
                SELECT
                    o.i                         as orderId,
                    o.product_id                as productId,
                    o.client_id                 as clientId,
                    o.quantity,
                    o.price,
                    o.status,
                    o.consignee_name            as consigneeName,
                    o.consignee_phone_number    as consigneePhoneNumber,
                    o.delivery_address          as deliveryAddress,
                    o.create_at                 as createAt,
                    o.canceled_at               as canceledAt,
                    o.completed_at              as completedAt
                FROM orders o
                """;
        List<String> whereList = new ArrayList<>();
        String where = "";

        if (Objects.nonNull(orderId)) {
            whereList.add("o.id = :orderId ");
        }

        if (whereList.size() != 0) {
            where = QueryUtil.createWhereQuery(whereList);
        }

        String query = select + where;
        return query;
    }

    private String createQueryGetAllOrderByAdmin(Integer orderStatus,
                                                 String fromDate,
                                                 String toDate,
                                                 String search){
        String sql = """
                select  o.id             as orderId,
                        o.price,
                        o.quantity,
                        o.total,
                        p.name          as productName,
                        o.status,
                        o.create_at     as createAt,
                        o.cancel_at     as cancelAt,
                        o.complete_at   as completeAt,
                        c.fullname
                        from orders o join client c on c.user_id = o.client_id
                                     join product p on p.id = o.product_id
                    
                """;
        List<String> whereList = new ArrayList<>();

        if (Objects.nonNull(orderStatus)) {
            whereList.add("o.status = :orderStatus ");
        }
        if (StringUtils.isNotBlank(search)) {
            whereList.add("c.full_name like :search or o.id like :search ");
        }

        if (StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)) {
            whereList.add("o.create_at >= :fromDate and o.create_at <= :toDate ");
        } else if (StringUtils.isNotBlank(fromDate)) {
            whereList.add("o.create_at >= :fromDate ");
        } else if (StringUtils.isNotBlank(toDate)) {
            whereList.add("o.create_at <= :toDate ");
        }

        String where = "";
        if (whereList.size() != 0) {
            where = QueryUtil.createWhereQuery(whereList);
        }

        String order = " order by o.id desc";

        String query = sql + where + order;

        return query;
    }
    private String createGetAllOrderQueryByClientQuery(Long userId, Integer orderStatus) {

        String select = """
                select o.id                 as orderId,
                       o.price,
                       o.quantity,
                       o.total,
                       o.status,
                       o.create_at          as createAt,
                       o.canceled_at        as canceledAt,
                       o.completed_at       as completedAt,
                from orders o""";

        List<String> whereList = new ArrayList<>();

        if (Objects.nonNull(userId)) {
            whereList.add("o.client_id = :userId ");
        }

        if (Objects.nonNull(orderStatus)) {
            whereList.add("o.status = :orderStatus ");
        }

        String where = "";
        if (whereList.size() != 0) {
            where = QueryUtil.createWhereQuery(whereList);
        }

        String order = " order by o.id desc";

        String query = select + where + order;

        return query;
    }

    private Map<String, Object> setParameterGetAllOrderByClient(Long userId, Integer orderStatus) {
        Map<String, Object> map = new HashMap<>();

        if (Objects.nonNull(userId)) {
            map.put("userId", userId);
        }

        if (Objects.nonNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }

        return map;
    }

    private Map<String, Object> setParameterGetAllOrderQueryByAdmin(Integer orderStatus, String fromDate, String toDate, String search) {

        Map<String, Object> map = new HashMap<>();

        if (Objects.nonNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }
        if (StringUtils.isNotBlank(search)) {
            map.put("search", "%" + search + "%");
        }

        if (StringUtils.isNotBlank(fromDate)) {
            map.put("fromDate", fromDate);
        }

        if (StringUtils.isNotBlank(toDate)) {
            toDate += " 23:59:59";
            map.put("toDate", toDate);
        }

        return map;
    }
    private Map<String, Object> setParameterGetOrderDetailByOrderId(Long orderId) {

        Map<String, Object> map = new HashMap<>();

        if (Objects.nonNull(orderId)) {
            map.put("orderId", orderId);
        }

        return map;
    }
}
