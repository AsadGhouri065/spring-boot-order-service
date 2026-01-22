package com.ghouri.orderservice.service;

import com.ghouri.orderservice.contracts.OrderServiceContract;
import com.ghouri.orderservice.dto.OrderDTO;
import com.ghouri.orderservice.dto.OrderRequestDTO;
import com.ghouri.orderservice.eventdtos.OrderPlacedEvent;
import com.ghouri.orderservice.model.Order;
import com.ghouri.orderservice.model.User;
import com.ghouri.orderservice.repository.OrderRepository;
import com.ghouri.orderservice.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderServiceContract {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<OrderDTO> getOrders() {
        var orders = orderRepository.findAll();
        return orders.stream().map(x -> new OrderDTO(x.getOrderNumber(), x.getTotalAmount(), x.getStatus())).toList();
    }

    @Override
    @Transactional
    public Order persistOrder(OrderRequestDTO orderDto) {
        var userRequestDTO = orderDto.userRequestDTO();

        var user = userRepository
                .findByEmail(userRequestDTO.email())
                .orElseGet(() -> {
                    var _user = new User(userRequestDTO.userName(), userRequestDTO.password(), userRequestDTO.email());
                    return userRepository.save(_user);
                });

        var order = new Order(orderDto.orderNumber(), orderDto.totalAmount(), orderDto.status());
        order.setUser(user);

        var savedOrder = orderRepository.save(order);

        // publish event here
        applicationEventPublisher.publishEvent(new OrderPlacedEvent(order, "ORDER_PLACED"));

        return savedOrder;
    }
}

