package com.ghouri.orderservice.dto;

import com.ghouri.orderservice.enums.PaymentMethod;

public record PaymentRequestDTO(
        PaymentMethod paymentMethod,
        Double amount,
        String currency,
        String paymentStatus
) {
}

