package com.product.payment.payment_service.service;
import com.product.payment.payment_service.dto.OrderCreatedEvent;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentProcessor {

    private final Random random = new Random();

    public void process(OrderCreatedEvent event) {
        System.out.println("Processing payment for order " + event.getOrderId());

        boolean success = random.nextBoolean(); // simulate gateway

        if (success) {
            System.out.println("Payment SUCCESS for order " + event.getOrderId());
            // next step: notify commerce-backend
        } else {
            System.out.println("Payment FAILED for order " + event.getOrderId());
        }
    }
}

