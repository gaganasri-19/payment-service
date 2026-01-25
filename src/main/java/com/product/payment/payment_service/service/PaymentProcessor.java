package com.product.payment.payment_service.service;
import com.product.payment.payment_service.client.CommerceClient;
import com.product.payment.payment_service.dto.OrderCreatedEvent;
import org.springframework.stereotype.Service;

//import java.util.Random;

@Service
public class PaymentProcessor {

    private final CommerceClient commerceClient;
    //private final Random random = new Random();

    public PaymentProcessor(CommerceClient commerceClient) {
        this.commerceClient = commerceClient;
    }

    public void process(OrderCreatedEvent event) {
       // boolean success = random.nextBoolean();

       // if (success) {
            commerceClient.notifyPaymentSuccess(event.getOrderId());
        // } else {
        //     commerceClient.notifyPaymentFailure(event.getOrderId());
        // }
    }
}

