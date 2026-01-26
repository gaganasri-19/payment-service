package com.product.payment.payment_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.product.payment.payment_service.dto.PayPalOrderResponse;

@Service
public class PayPalOrderService {

    private final WebClient webClient;
    private final PayPalAuthService authService;

    public PayPalOrderService(WebClient webClient, PayPalAuthService authService) {
        this.webClient = webClient;
        this.authService = authService;
    }

    public String createOrder(Long orderId, Double amount) {

        String token = authService.getAccessToken();

        return webClient.post()
                .uri("/v2/checkout/orders")
                .headers(h -> h.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(buildOrderRequest(orderId, amount.toString()))
                .retrieve()
                .bodyToMono(PayPalOrderResponse.class)
                .block()
                .getId();
    }

    private Map<String, Object> buildOrderRequest(Long orderId, String amount) {

        return Map.of(
            "intent", "CAPTURE",
            "purchase_units", List.of(
                Map.of(
                    "reference_id", orderId.toString(),
                    "amount", Map.of(
                        "currency_code", "USD",
                        "value", amount
                    )
                )
            )
        );
    }
}
