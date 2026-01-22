package com.ghouri.orderservice.service;

import com.ghouri.orderservice.components.DiscountStrategyFactory;
import com.ghouri.orderservice.contracts.DiscountPolicy;
import com.ghouri.orderservice.enums.CustomerType;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    private final DiscountStrategyFactory discountStrategyFactory;

    public DiscountService(DiscountStrategyFactory discountStrategyFactory) {
        this.discountStrategyFactory = discountStrategyFactory;
    }

    public double calculateDiscount(CustomerType customerType, double price) {
        DiscountPolicy discountPolicy = discountStrategyFactory.getPolicy(customerType);
        return discountPolicy.calculateDiscount(price);
    }
}

@Service
class RegularDiscount implements DiscountPolicy {

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.REGULAR;
    }

    @Override
    public double calculateDiscount(double price) {
        return price * 0.95;
    }
}

@Service
class PremiumDiscount implements DiscountPolicy {
    @Override
    public CustomerType getCustomerType() {
        return CustomerType.PREMIUM;
    }

    @Override
    public double calculateDiscount(double price) {
        return price * 0.90;
    }
}

@Service
class VipDiscount implements DiscountPolicy {
    @Override
    public CustomerType getCustomerType() {
        return CustomerType.VIP;
    }

    @Override
    public double calculateDiscount(double price) {
        return price * 0.85;
    }
}