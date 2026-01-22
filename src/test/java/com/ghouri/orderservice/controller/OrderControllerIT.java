package com.ghouri.orderservice.controller;

import com.ghouri.orderservice.dto.OrderRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    public static String URL = "/orders";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getOrdersIntegrationTest() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void persistOrder() throws Exception {
        var orderRequestDto = new OrderRequestDTO(
                2000.0,
                "order_number",
                "PENDING",
                new OrderRequestDTO.UserRequestDTO(
                        "john doe",
                        "hidden",
                        "john@gmail.com"
                )
        );

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(orderRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
