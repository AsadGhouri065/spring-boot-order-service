package com.ghouri.orderservice.service;

import com.ghouri.orderservice.contracts.PaymentStrategy;
import com.ghouri.orderservice.enums.PaymentMethod;
import com.ghouri.orderservice.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PayPalPayment implements PaymentStrategy {
    @Override
    public Payment processPayment(Double amount) {
        return new Payment(amount, "USD", PaymentMethod.PAYPAL, "PENDING");
    }

    @Override
    public PaymentMethod getSupportedPayment() {
        return PaymentMethod.PAYPAL;
    }
}
