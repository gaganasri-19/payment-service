package com.product.payment.payment_service.dto;
public class OrderCreatedEvent {

    private Long orderId;
    private String userEmail;
    private Double amount;

      // Default constructor
    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, String userEmail, Double amount) {
        this.orderId = orderId;
        this.userEmail = userEmail;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Double getAmount() {
        return amount;
    }
}