package com.ghouri.orderservice.contracts;

import com.ghouri.orderservice.dto.OrderDTO;
import com.ghouri.orderservice.dto.OrderRequestDTO;
import com.ghouri.orderservice.model.Order;

import java.util.List;

public interface OrderServiceContract {
    List<OrderDTO> getOrders();

    Order persistOrder(OrderRequestDTO sampleOrder);
}
