package com.ghouri.orderservice.eventdtos;

import com.ghouri.orderservice.model.Order;

public record OrderPlacedEvent(
        Order order,
        String eventType
) {
}
