package com.example.demo.controller;

import com.example.demo.dto.auth.request.RegistRequest;
import com.example.demo.dto.base.BaseResponse;
import com.example.demo.dto.client.request.ClientEdditInfoRequest;
import com.example.demo.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody @Valid RegistRequest registRequest){
        try {
            clientService.registerClient(registRequest);
            return ResponseEntity.ok(new BaseResponse(200, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(500, e.getMessage(), null));
        }
    }
    @PostMapping("/edit-info")
    public ResponseEntity<BaseResponse> editInfo(@RequestBody ClientEdditInfoRequest request) {
        try {
            clientService.editInfo(request);
            return ResponseEntity.ok(new BaseResponse(200, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(500, e.getMessage(), null));
        }
    }
}
