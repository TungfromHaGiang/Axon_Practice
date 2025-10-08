package com.mss.orderservice.envent;

public class ShippingProcessedEvent {
    private String shippingId;
    private String orderId;
    private String userId;
    private String paymentId;
    private double amount;
    private String status;
}
