package com.example.demo.util;

import com.example.demo.exception.Exception;

import java.util.List;

public class QueryUtil {

    /**
     * Kiểm tra và trả về column name cho sorting (Admin)
     */
    public static String checkOrderSearchProduct(String order) throws Exception {
        return switch (order) {
            case "productId" -> "id";  // ✅ Tên cột trong DB
            case "productName" -> "name";
            case "price" -> "price";
            case "latestPrice" -> "price";  // hoặc calculated field
            case "sold" -> "sold";
            case "createAt" -> "created_at";  // ✅ Snake case trong DB
            default -> throw new Exception("Order query not match: " + order);
        };
    }

    /**
     * Kiểm tra và trả về column name cho sorting (Client)
     */
    public static String checkOrderSearchProductByClient(String order) throws Exception {
        return switch (order) {
            case "productId" -> "id";
            case "price" -> "price";
            case "sold" -> "sold";
            case "createAt" -> "created_at";
            default -> throw new Exception("Order query not match: " + order);
        };
    }

    /**
     * Tạo WHERE clause từ list conditions
     */
    public static String createWhereQuery(List<String> whereList) {
        if (whereList == null || whereList.isEmpty()) {
            return "";
        }

        StringBuilder where = new StringBuilder("WHERE ");
        where.append(whereList.get(0));

        for (int i = 1; i < whereList.size(); i++) {
            where.append(" AND ").append(whereList.get(i));
        }

        return where.toString();
    }

    /**
     * Tạo IN condition từ list integers
     */
    public static String createInConditionQuery(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "()";
        }

        StringBuilder condition = new StringBuilder("(");
        condition.append(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            condition.append(", ").append(list.get(i));
        }

        condition.append(")");
        return condition.toString();
    }

    /**
     * Tạo IN condition từ list Long (cho IDs)
     */
    public static String createInConditionQueryForLong(List<Long> list) {
        if (list == null || list.isEmpty()) {
            return "()";
        }

        StringBuilder condition = new StringBuilder("(");
        condition.append(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            condition.append(", ").append(list.get(i));
        }

        condition.append(")");
        return condition.toString();
    }
}