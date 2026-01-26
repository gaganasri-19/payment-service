package com.product.payment.payment_service.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.payment.payment_service.service.PaymentProcessor;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/api/paypal")
public class PayPalWebhookController {

    private final PaymentProcessor paymentProcessor;

    public PayPalWebhookController(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @PostMapping("/webhook")
    @Transactional
    public ResponseEntity<String> handleWebhook(
        @RequestBody Map<String, Object> payload,
        @RequestHeader Map<String, String> headers
    ) {
    paymentProcessor.process(payload);
    return ResponseEntity.ok().body("Payment status updated");
    }

}

