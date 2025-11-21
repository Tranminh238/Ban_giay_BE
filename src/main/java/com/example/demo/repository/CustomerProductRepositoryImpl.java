package com.example.demo.repository;

import com.example.demo.dto.product.request.ProductSearchRequest;
import com.example.demo.dto.product.response.ProductResponse;
import com.example.demo.repository.CustomerProductRepository;
import com.example.demo.util.QueryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerProductRepositoryImpl implements CustomerProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<ProductResponse> getSearchProduct(ProductSearchRequest request) throws Exception {
        try{

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    private String createGetProductQuery(ProductSearchRequest request){
        String select =
                """
                        select  p.id            as productId,
                                p.category_id   as categoryId,
                                p.name          as productName,
                                p.price,
                                p.stock,
                                p.discount,
                                p.brand,
                                p.description,
                                p.created_at as createAt
                        from product p  \s""";
        List<String> whereList = new ArrayList<>();
        whereList.add("p.status = 1 ");
        if (Objects.nonNull(request.getCategoryId())) {
            whereList.add("p.category_id = :categoryId ");
        }
        if (StringUtils.isNotBlank(request.getName())) {
            whereList.add("p.name like :productName ");
        }
        if (Objects.nonNull(request.getMinPrice()) && Objects.nonNull(request.getMaxPrice())) {
            whereList.add("p.latest_price >= :minPrice and p.latest_price <= :maxPrice ");
        } else if (Objects.nonNull(request.getMinPrice())) {
            whereList.add("p.latest_price >= :minPrice ");
        } else if (Objects.nonNull(request.getMaxPrice())) {
            whereList.add("p.latest_price <= :maxPrice ");
        }
        String where = "";
        if (whereList.size() != 0) {
            where = QueryUtil.createWhereQuery(whereList);
        }
        String order = " order by ";
        if (StringUtils.isNotBlank(request.getSortBy())) {
            try {
                order += QueryUtil.checkOrderSearchProductByClient(request.getSortBy());
            } catch (Exception e) {
                log.warn("Invalid sort field: {}, using default", request.getSortBy());
                order += "p.name";
            }
        } else {
            order += "p.created_at ";
        }

        if (StringUtils.isNotBlank(request.getSortDirection())) {
            order += request.getSortDirection();
        } else {
            order += "desc";
        }
        String query = select + where + order;
        return query;
    }
}