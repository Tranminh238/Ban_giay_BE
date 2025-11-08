package com.example.demo.dto.client.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientEdditInfoRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
}
