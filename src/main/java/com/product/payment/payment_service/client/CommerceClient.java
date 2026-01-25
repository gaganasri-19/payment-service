package com.product.payment.payment_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommerceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL;

    private CommerceClient(@Value("${commerce.service.url}") String commerceServiceUrl) {
        this.BASE_URL = commerceServiceUrl;
    }

    public void notifyPaymentSuccess(Long orderId) {
        restTemplate.postForEntity(
            BASE_URL + "/api/internal/payments/success/" + orderId,
            null,
            Void.class
        );
    }

    public void notifyPaymentFailure(Long orderId) {
        restTemplate.postForEntity(
            BASE_URL + "/api/internal/payments/failed/" + orderId,
            null,
            Void.class
        );
    }
}

