package com.ghouri.orderservice.controller;

import com.ghouri.orderservice.dto.PaymentRequestDTO;
import com.ghouri.orderservice.service.PaymentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
public class PaymentController {

    private final PaymentServiceImpl paymentServiceImpl;

    public PaymentController(PaymentServiceImpl paymentServiceImpl) {
        this.paymentServiceImpl = paymentServiceImpl;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        paymentServiceImpl.processPayment(paymentRequestDTO);
    }
}
