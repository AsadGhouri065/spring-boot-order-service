package com.ghouri.orderservice.components;

import com.ghouri.orderservice.contracts.PaymentStrategy;
import com.ghouri.orderservice.enums.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentStrategyFactory {
    private final Map<PaymentMethod, PaymentStrategy> strategies = new EnumMap<>(PaymentMethod.class);

    private PaymentStrategyFactory(List<PaymentStrategy> paymentStrategies) {
        for (PaymentStrategy paymentStrategy : paymentStrategies) {
            strategies.put(paymentStrategy.getSupportedPayment(), paymentStrategy);
        }
    }

    public PaymentStrategy getStrategy(PaymentMethod paymentMethod) {
        return strategies.get(paymentMethod);
    }
}
