package com.ghouri.orderservice.contracts;

import com.ghouri.orderservice.enums.PaymentMethod;
import com.ghouri.orderservice.model.Payment;

public interface PaymentStrategy {
    Payment processPayment(Double amount);
    PaymentMethod getSupportedPayment();
}
