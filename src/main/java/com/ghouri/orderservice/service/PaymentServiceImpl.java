package com.ghouri.orderservice.service;

import com.ghouri.orderservice.components.PaymentStrategyFactory;
import com.ghouri.orderservice.contracts.PaymentStrategy;
import com.ghouri.orderservice.dto.PaymentRequestDTO;
import com.ghouri.orderservice.model.Payment;
import com.ghouri.orderservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentStrategyFactory paymentStrategyFactory) {
        this.paymentRepository = paymentRepository;
        this.paymentStrategyFactory = paymentStrategyFactory;

    }

    @Transactional
    public void processPayment(PaymentRequestDTO paymentRequestDTO) {
        // call factory to get strategy
        PaymentStrategy strategy = paymentStrategyFactory.getStrategy(paymentRequestDTO.paymentMethod());

        // call pay
        Payment payment = strategy.processPayment(paymentRequestDTO.amount());

        // call repo and save payment
        paymentRepository.save(payment);
    }
}
