package com.ghouri.orderservice.model;

import com.ghouri.orderservice.enums.PaymentMethod;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private Double amount;

    @Column
    private String currency;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    public Payment(Double amount, String currency, PaymentMethod paymentMethod, String paymentStatus) {
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public Payment() {

    }
}
