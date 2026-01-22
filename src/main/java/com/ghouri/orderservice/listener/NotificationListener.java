package com.ghouri.orderservice.listener;

import com.ghouri.orderservice.eventdtos.OrderPlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @EventListener
    public void handleOrderCreation(OrderPlacedEvent orderPlacedEvent) {
        System.out.println("Order was placed... " + orderPlacedEvent.order() + orderPlacedEvent.eventType());
    }
}
