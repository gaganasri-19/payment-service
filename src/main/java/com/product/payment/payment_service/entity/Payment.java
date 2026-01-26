package com.product.payment.payment_service.entity;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false, unique = true)
    private String paypalOrderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    public Payment() {}

    public Payment(Long orderId, String paypalOrderId, PaymentStatus status) {
        this.orderId = orderId;
        this.paypalOrderId = paypalOrderId;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // getters & setters
    public Long getOrderId() {
        return orderId;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }
}
