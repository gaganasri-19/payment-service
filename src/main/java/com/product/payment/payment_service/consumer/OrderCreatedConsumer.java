package com.product.payment.payment_service.consumer;

import com.product.payment.payment_service.dto.OrderCreatedEvent;
import com.product.payment.payment_service.service.PaymentProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final PaymentProcessor paymentProcessor;

    public OrderCreatedConsumer(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @KafkaListener(
        topics = "order.created",
        groupId = "payment-service-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderCreated(OrderCreatedEvent event) {
        paymentProcessor.process(event);
    }
}

