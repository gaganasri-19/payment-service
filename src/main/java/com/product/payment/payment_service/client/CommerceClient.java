package com.product.payment.payment_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class CommerceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL;

    public CommerceClient(@Value("${commerce.service.url}") String commerceServiceUrl) {
        this.BASE_URL = commerceServiceUrl;
    }

    @Retry(name = "commerceService")
    @CircuitBreaker(name = "commerceService", fallbackMethod = "fallback")
    public void notifyPaymentSuccess(Long orderId) {
        restTemplate.postForEntity(
            BASE_URL + "/api/internal/payments/success/" + orderId,
            null,
            Void.class
        );
    }

    @Retry(name = "commerceService")
    @CircuitBreaker(name = "commerceService", fallbackMethod = "fallback")
    public void notifyPaymentFailure(Long orderId) {
        restTemplate.postForEntity(
            BASE_URL + "/api/internal/payments/failed/" + orderId,
            null,
            Void.class
        );
    }

    public void fallback(Long orderId, Throwable ex) {
    System.out.println("Fallback triggered for order " + orderId);
    }
}

