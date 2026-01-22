package com.ghouri.orderservice.dto;

public record OrderDTO(
        String orderNumber,
        double totalAmount,
        String status
) {
}
