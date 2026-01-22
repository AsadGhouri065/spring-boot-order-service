package com.ghouri.orderservice.components;

import com.ghouri.orderservice.contracts.DiscountPolicy;
import com.ghouri.orderservice.enums.CustomerType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DiscountStrategyFactory {
    private final Map<CustomerType, DiscountPolicy> policies = new EnumMap<>(CustomerType.class);

    private DiscountStrategyFactory(List<DiscountPolicy> discountPolicyList) {
        for (var policy : discountPolicyList) {
            policies.put(policy.getCustomerType(), policy);
        }
    }

    public DiscountPolicy getPolicy(CustomerType customerType) {
        return policies.get(customerType);
    }
}
