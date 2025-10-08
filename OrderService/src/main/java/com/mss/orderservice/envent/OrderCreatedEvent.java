package com.mss.orderservice.envent;

public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private double price;
    private String status = "ORDERED";
}
