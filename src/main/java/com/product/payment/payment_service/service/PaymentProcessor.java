package com.product.payment.payment_service.service;
import com.product.payment.payment_service.client.CommerceClient;
import com.product.payment.payment_service.dto.OrderCreatedEvent;
import com.product.payment.payment_service.entity.Payment;
import com.product.payment.payment_service.entity.PaymentStatus;
import com.product.payment.payment_service.repository.PaymentRepository;

import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class PaymentProcessor {

    private final CommerceClient commerceClient;
    private final PayPalOrderService payPalOrderService;
    private final PaymentRepository paymentRepository;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentProcessor.class);

    public PaymentProcessor(CommerceClient commerceClient, PayPalOrderService payPalOrderService, PaymentRepository paymentRepository) {
        this.commerceClient = commerceClient;
        this.payPalOrderService = payPalOrderService;
        this.paymentRepository = paymentRepository;
    }

    public void process(OrderCreatedEvent event) {

        String payPalOrderId = payPalOrderService.createOrder(event.getOrderId(), event.getAmount());
        Payment payment = new Payment(event.getOrderId(), payPalOrderId, PaymentStatus.INITIATED);
        paymentRepository.save(payment);
    }

    @SuppressWarnings("unchecked")
    public void process(Map<String, Object> payload) {

    String eventType = (String) payload.get("event_type");

    Map<String, Object> resource =
            (Map<String, Object>) payload.get("resource");

    String paypalOrderId = (String) resource.get("id");

    Payment payment = paymentRepository
            .findByPaypalOrderId(paypalOrderId)
            .orElseThrow();

    switch (eventType) {
        case "CHECKOUT.ORDER.APPROVED" -> handleSuccess(payment);
        case "PAYMENT.CAPTURE.DENIED",
             "CHECKOUT.ORDER.CANCELLED" -> handleFailure(payment);
        default -> log.info("Ignoring event {}", eventType);
        }
    }

    private void handleSuccess(Payment payment) {
    payment.setStatus(PaymentStatus.SUCCESS);
    commerceClient.notifyPaymentSuccess(payment.getOrderId());
    }

    private void handleFailure(Payment payment) {
    payment.setStatus(PaymentStatus.FAILED);
    commerceClient.notifyPaymentFailure(payment.getOrderId());
    }

}

