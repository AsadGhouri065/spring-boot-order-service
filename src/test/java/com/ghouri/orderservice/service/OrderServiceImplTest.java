package com.ghouri.orderservice.service;

import com.ghouri.orderservice.contracts.OrderServiceContract;
import com.ghouri.orderservice.dto.OrderRequestDTO;
import com.ghouri.orderservice.repository.OrderRepository;
import com.ghouri.orderservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceContract orderServiceContract;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

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
}
