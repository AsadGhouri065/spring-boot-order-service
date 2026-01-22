package com.ghouri.orderservice.controller;


import com.ghouri.orderservice.contracts.OrderServiceContract;
import com.ghouri.orderservice.dto.OrderDTO;
import com.ghouri.orderservice.dto.OrderRequestDTO;
import com.ghouri.orderservice.model.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderServiceContract orderServiceContract;

    @Autowired
    private ObjectMapper objectMapper;

    private String URL = "/orders";

    @Test
    @WithMockUser
    void getOrders() throws Exception {
        var listOfOrders = List.of(new OrderDTO("order-number-1", 1000.0, "Pending"), new OrderDTO("order-number-2", 120.0, "Pending"));

        given(orderServiceContract.getOrders()).willReturn(listOfOrders);

        mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));

        Mockito.verify(orderServiceContract).getOrders();
    }

    @Test
    @WithMockUser
    void persistOrder() throws Exception {
        var sampleOrder = new OrderRequestDTO(2500.0, "order-number", "Pending", new OrderRequestDTO.UserRequestDTO(
                "Asad", "hidden", "hello@gmail.com"
        ));
        var savedOrder = new Order(UUID.randomUUID(), "order-number", 2500.0, "Pending");

        given(orderServiceContract.persistOrder(sampleOrder)).willReturn(savedOrder);

        mockMvc.perform(post(URL)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleOrder)))
                .andExpect(status().isCreated());

        Mockito.verify(orderServiceContract).persistOrder(sampleOrder);
    }
}
