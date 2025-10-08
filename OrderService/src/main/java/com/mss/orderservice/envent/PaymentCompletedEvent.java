package com.mss.orderservice.envent;

public class PaymentCompletedEvent {
    private String paymentId;
    private String orderId;
    private String userId;
    private double price;
    private String status = "SUCCESS";
}
