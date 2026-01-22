package com.ghouri.orderservice.contracts;

import com.ghouri.orderservice.enums.CustomerType;

public interface DiscountPolicy {
    double calculateDiscount(double price);

    CustomerType getCustomerType();
}
