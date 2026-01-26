package com.product.payment.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.payment.payment_service.entity.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaypalOrderId(String paypalOrderId);
    Optional<Payment> findByOrderId(Long orderId);
}

