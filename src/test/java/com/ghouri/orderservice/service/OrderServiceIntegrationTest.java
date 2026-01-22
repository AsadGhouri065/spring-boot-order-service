package com.ghouri.orderservice.service;

import com.ghouri.orderservice.contracts.OrderServiceContract;
import com.ghouri.orderservice.dto.OrderRequestDTO;
import com.ghouri.orderservice.eventdtos.OrderPlacedEvent;
import com.ghouri.orderservice.listener.NotificationListener;
import com.ghouri.orderservice.model.Order;
import com.ghouri.orderservice.repository.OrderRepository;
import com.ghouri.orderservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderServiceContract orderServiceContract;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("Transactional Test")
    void persistOrder_rollsBackOnException() {
        var dto = new OrderRequestDTO(
                100.0,
                "ORD-1",
                "PENDING",
                new OrderRequestDTO.UserRequestDTO(
                        "john",
                        "pwd",
                        "john@example.com"
                )
        );

        assertThrows(RuntimeException.class, () -> orderServiceContract.persistOrder(dto));

        // After exception, nothing should be committed
        assertTrue(orderRepository.findAll().isEmpty());
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void persistOrder_HappyFlow() {
        var dto = new OrderRequestDTO(
                100.0,
                "ORD-1",
                "PENDING",
                new OrderRequestDTO.UserRequestDTO(
                        "john",
                        "pwd",
                        "john@example.com"
                )
        );

        Order order = orderServiceContract.persistOrder(dto);

        assertNotNull(orderRepository.findAll());
        assertNotNull(userRepository.findAll());

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order, "ORDER_PLACED");
        applicationEventPublisher.publishEvent(orderPlacedEvent);

        assertEquals(order.getOrderNumber(), orderPlacedEvent.order().getOrderNumber());
        assertEquals(order.getStatus(), orderPlacedEvent.order().getStatus());
        assertEquals(order.getTotalAmount(), orderPlacedEvent.order().getTotalAmount());
    }
}
