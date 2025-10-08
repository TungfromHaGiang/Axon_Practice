package com.mss.orderservice.envent;

public class PaymentProcessedEvent {
    private String paymentId;
    private String orderId;
    private String userId;
    private double amount;
    private String status;
}
