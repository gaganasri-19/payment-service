package com.product.payment.payment_service.entity;

public enum PaymentStatus {
    INITIATED,    // PayPal order created, waiting for user approval
    SUCCESS,  // Payment captured successfully
    FAILED      // Payment failed or denied
}

