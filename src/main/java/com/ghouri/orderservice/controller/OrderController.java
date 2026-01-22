package com.ghouri.orderservice.controller;

import com.ghouri.orderservice.contracts.OrderServiceContract;
import com.ghouri.orderservice.dto.OrderDTO;
import com.ghouri.orderservice.dto.OrderRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderServiceContract orderServiceContract;

    public OrderController(OrderServiceContract orderServiceContract) {
        this.orderServiceContract = orderServiceContract;
    }

    @GetMapping
    public List<OrderDTO> getOrders() {
        return orderServiceContract.getOrders();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void persistOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        orderServiceContract.persistOrder(orderRequestDTO);
    }
}


