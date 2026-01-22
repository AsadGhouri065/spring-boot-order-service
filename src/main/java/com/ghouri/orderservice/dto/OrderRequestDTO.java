package com.ghouri.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderRequestDTO(
        double totalAmount,
        String orderNumber,
        String status,

        @JsonProperty("userInfo")
        UserRequestDTO userRequestDTO
) {
    public record UserRequestDTO(
            String userName,
            String password,
            String email
    ) {
    }
}


