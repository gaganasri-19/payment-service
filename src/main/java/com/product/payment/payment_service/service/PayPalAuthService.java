package com.product.payment.payment_service.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.product.payment.payment_service.dto.PayPalTokenResponse;

@Service
public class PayPalAuthService {

    private final WebClient webClient;

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    public PayPalAuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getAccessToken() {

        return webClient.post()
                .uri("/v1/oauth2/token")
                .headers(h -> h.setBasicAuth(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(PayPalTokenResponse.class)
                .block()
                .getAccessToken();
    }
}
