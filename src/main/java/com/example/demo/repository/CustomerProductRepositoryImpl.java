//package com.example.demo.repository;
//
//import com.example.demo.dto.product.request.ProductSearchRequest;
//import com.example.demo.dto.product.response.ProductResponse;
//import com.example.demo.repository.CustomerProductRepository;
//import com.example.demo.util.PageUtil;
//import com.example.demo.util.QueryUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class CustomerProductRepositoryImpl implements CustomerProductRepository {
//
//    private final NamedParameterJdbcTemplate jdbcTemplate;
//    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    @Override
//    public Page<ProductResponse> getSearchProduct(ProductSearchRequest request) throws Exception {
//        try{
//            List<ProductResponse> productResponses = jdbcTemplate.query(
//                    createGetProductQuery(request),
//                    setParmSearch(request),
////                    (rs, rowNum) -> new ProductResponse(
////                            rs.getLong("productId"),
////                            rs.getString("productName"),
////                            rs.getInt("price"),
////                            rs.getInt("sold"),
////                            rs.getLong("discount"),
////                            rs.getString("brand"),
////                            rs.getString("description"),
////                            LocalDateTime.parse(rs.getString("createAt"), dateTimeFormatter),
////
////                            )
////            );
//                    (rs, rowNum) -> ProductResponse.builder()
//                            .productId(rs.getLong("productId"))
//                            .name(rs.getString("productName"))
//                            .price(
//                                    Objects.isNull(rs.getBigDecimal("price"))
//                                            ? null
//                                            : rs.getBigDecimal("price").intValue()
//                            )
//                            .sold(
//                                    Objects.isNull(rs.getBigDecimal("sold"))
//                                            ? null
//                                            : rs.getBigDecimal("sold").intValue()
//                            )
//                            .discount(
//                                    Objects.isNull(rs.getBigDecimal("discount"))
//                                            ? null
//                                            : rs.getBigDecimal("discount").intValue()
//                            )
//                            .brand(rs.getString("brand"))
//                            .description(rs.getString("description"))
//                            .createdAt(
//                                    StringUtils.isNotBlank(rs.getString("createdAt"))
//                                            ? LocalDateTime.parse(rs.getString("createdAt"), dateTimeFormatter)
//                                            : null
//                            )
//                            .build()
//            );
//            return PageUtil.getPage(productResponses, request.getPage(), request.getSize());
//
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }
//    private String createGetProductQuery(ProductSearchRequest request){
//        String select =
//                """
//                        select  p.id            as productId,
//                                p.name          as productName,
//                                p.price,
//                                p.sold,
//                                p.discount,
//                                p.brand,
//                                p.description,
//                                p.created_at as createAt
//                        from product p  \s""";
//        List<String> whereList = new ArrayList<>();
//        whereList.add("p.status = 1 ");
//        if (StringUtils.isNotBlank(request.getName())) {
//            whereList.add("p.name like :productName ");
//        }
//        if (Objects.nonNull(request.getMinPrice()) && Objects.nonNull(request.getMaxPrice())) {
//            whereList.add("p.price >= :minPrice and p.price <= :maxPrice ");
//        } else if (Objects.nonNull(request.getMinPrice())) {
//            whereList.add("p.price >= :minPrice ");
//        } else if (Objects.nonNull(request.getMaxPrice())) {
//            whereList.add("p.price <= :maxPrice ");
//        }
//        if (Objects.nonNull(request.getDiscount())) {
//            whereList.add("p.discount = :discount ");
//        }
//        if (StringUtils.isNotBlank(request.getBrand())) {
//            whereList.add("p.brand = :brand ");
//        }
//        String where = "";
//        if (whereList.size() != 0) {
//            where = QueryUtil.createWhereQuery(whereList);
//        }
//        String order = " order by ";
//        if (StringUtils.isNotBlank(request.getSortBy())) {
//            try {
//                order += QueryUtil.checkOrderSearchProductByClient(request.getSortBy());
//            } catch (Exception e) {
//                log.warn("Invalid sort field: {}, using default", request.getSortBy());
//                order += "p.name";
//            }
//        } else {
//            order += "p.created_at ";
//        }
//
//        if (StringUtils.isNotBlank(request.getSortDirection())) {
//            order += request.getSortDirection();
//        } else {
//            order += "desc";
//        }
//        String query = select + where + order;
//        return query;
//    }
//    private Map<String, Object> setParmSearch(ProductSearchRequest request){
//        Map<String, Object> map = new HashMap<>();
//
//        if (StringUtils.isNotBlank(request.getName())) {
//            map.put("productName", "%" + request.getName() + "%");
//        }
//
//        if (Objects.nonNull(request.getMinPrice())) {
//            map.put("minPrice", request.getMinPrice());
//        }
//
//        if (Objects.nonNull(request.getMaxPrice())) {
//            map.put("maxPrice", request.getMaxPrice());
//        }
//
//        if (Objects.nonNull(request.getDiscount())) {
//            map.put("discount", request.getDiscount());
//        }
//
//        if (StringUtils.isNotBlank(request.getBrand())) {
//            map.put("brand", request.getBrand());
//        }
//
//        return map;
//    }
//
//
//}


package com.example.demo.repository;

import com.example.demo.dto.product.request.ProductSearchRequest;
import com.example.demo.dto.product.response.ProductResponse;
import com.example.demo.util.PageUtil;
import com.example.demo.util.QueryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerProductRepositoryImpl implements CustomerProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<ProductResponse> getSearchProduct(ProductSearchRequest request) throws Exception {
        try {
            List<ProductResponse> productResponses = jdbcTemplate.query(
                    createGetProductQuery(request),
                    setParmSearch(request),
                    (rs, rowNum) -> ProductResponse.builder()
                            .productId(rs.getLong("productId"))
                            .name(rs.getString("productName"))
                            .price(rs.getBigDecimal("price") != null
                                    ? rs.getBigDecimal("price").intValue()
                                    : null)
                            .sold(rs.getBigDecimal("sold") != null
                                    ? rs.getBigDecimal("sold").intValue()
                                    : null)
                            .quantity(rs.getBigDecimal("quantity") != null
                                    ? rs.getBigDecimal("quantity").intValue()
                                    : null)
                            .discount(rs.getBigDecimal("discount") != null
                                    ? rs.getBigDecimal("discount").intValue()
                                    : null)
                            .brand(rs.getString("brand"))
                            .description(rs.getString("description"))
                            .createdAt(
                                    rs.getString("createAt") != null
                                            ? LocalDateTime.parse(rs.getString("createAt"), dateTimeFormatter)
                                            : null
                            )
                            .build()
            );

            return PageUtil.getPage(productResponses, request.getPage(), request.getSize());

        } catch (Exception e) {
            log.error("Error searching products: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    private String createGetProductQuery(ProductSearchRequest request) {

        String select =
                """
                        select  p.id            as productId,
                                p.name          as productName,
                                p.price,
                                p.sold,
                                p.quantity,
                                p.discount,
                                p.brand,
                                p.description,
                                p.created_at    as createAt
                        from product p
                        """;

        List<String> whereList = new ArrayList<>();

        // luôn lấy status = 1
        whereList.add("p.status = 1");

        if (StringUtils.isNotBlank(request.getName())) {
            whereList.add("p.name LIKE :productName");
        }

        if (Objects.nonNull(request.getMinPrice()) && Objects.nonNull(request.getMaxPrice())) {
            whereList.add("p.price >= :minPrice AND p.price <= :maxPrice");
        } else if (Objects.nonNull(request.getMinPrice())) {
            whereList.add("p.price >= :minPrice");
        } else if (Objects.nonNull(request.getMaxPrice())) {
            whereList.add("p.price <= :maxPrice");
        }

        if (Objects.nonNull(request.getDiscount())) {
            whereList.add("p.discount = :discount");
        }

        if (StringUtils.isNotBlank(request.getBrand())) {
            whereList.add("p.brand = :brand");
        }

        String where = "";
        if (!whereList.isEmpty()) {
            where = QueryUtil.createWhereQuery(whereList);
        }

        // ORDER BY
        String order = " ORDER BY ";
        if (StringUtils.isNotBlank(request.getSortBy())) {
            try {
                order += QueryUtil.checkOrderSearchProductByClient(request.getSortBy());
            } catch (Exception e) {
                log.warn("Invalid sort field '{}', using default name DESC", request.getSortBy());
                order += "p.name";
            }
        } else {
            order += "p.created_at";
        }

        if (StringUtils.isNotBlank(request.getSortDirection())) {
            order += " " + request.getSortDirection();
        } else {
            order += " DESC";
        }

        return select + where + order;
    }

    private Map<String, Object> setParmSearch(ProductSearchRequest request) {
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotBlank(request.getName())) {
            map.put("productName", "%" + request.getName() + "%");
        }

        if (Objects.nonNull(request.getMinPrice())) {
            map.put("minPrice", request.getMinPrice());
        }

        if (Objects.nonNull(request.getMaxPrice())) {
            map.put("maxPrice", request.getMaxPrice());
        }

        if (Objects.nonNull(request.getDiscount())) {
            map.put("discount", request.getDiscount());
        }

        if (StringUtils.isNotBlank(request.getBrand())) {
            map.put("brand", request.getBrand());
        }

        return map;
    }
}
