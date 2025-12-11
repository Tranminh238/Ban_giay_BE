package com.example.demo.service;

import com.example.demo.dto.order.request.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
    public ByteArrayInputStream exportOrdersToExcel() throws IOException {

        List<Order> orders = orderRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Orders");

        // Tạo header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Order ID");
        header.createCell(1).setCellValue("Product ID");
        header.createCell(2).setCellValue("Client ID");
        header.createCell(3).setCellValue("Quantity");
        header.createCell(4).setCellValue("Price");
        header.createCell(5).setCellValue("Total");
        header.createCell(6).setCellValue("Status");
        header.createCell(7).setCellValue("Consignee Name");
        header.createCell(8).setCellValue("Phone");
        header.createCell(9).setCellValue("Address");
        header.createCell(10).setCellValue("Created At");

        int rowIndex = 1;

        for (Order order : orders) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getProductId());
            row.createCell(2).setCellValue(order.getClientId());
            row.createCell(3).setCellValue(order.getQuantity());
            row.createCell(4).setCellValue(order.getPrice());

            Long total = order.getQuantity() * order.getPrice();
            row.createCell(5).setCellValue(total);

            row.createCell(6).setCellValue(order.getStatus());
            row.createCell(7).setCellValue(order.getConsigneeName());
            row.createCell(8).setCellValue(order.getConsigneePhoneNumber());
            row.createCell(9).setCellValue(order.getDeliveryAddress());
            row.createCell(10).setCellValue(order.getCreateAt().toString());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }


}
