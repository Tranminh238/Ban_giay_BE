package com.example.demo.controller;

import com.example.demo.dto.base.BaseResponse;
import com.example.demo.dto.order.request.OrderRequest;
import com.example.demo.dto.order.request.OrderSearchRequest;
import com.example.demo.dto.order.response.OrderResponse;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final VNPayService vnPayService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createOrder(@RequestBody OrderRequest request) {
        try {
            orderService.createOrder(request);
            return ResponseEntity.ok(new BaseResponse(200, "Tạo order thành công", null));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponse(500, "Tạo order thất bại", null));

        }
    }

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<?> payment(@PathVariable Long orderId, HttpServletRequest request) {
        try {
            String paymentUrl = vnPayService.payment(orderId, request);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("export")
    public ResponseEntity<InputStreamResource> exportOrders() throws IOException {
        ByteArrayInputStream excelFile = orderService.exportOrdersToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelFile));
    }


}