package com.product.payment.payment_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PayPalTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
