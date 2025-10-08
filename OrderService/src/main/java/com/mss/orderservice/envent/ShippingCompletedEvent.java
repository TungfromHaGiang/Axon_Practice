package com.mss.orderservice.envent;

public class ShippingCompletedEvent {
    private String shippingId;
    private String orderId;
    private String userId;
    private String status = "SUCCESS";
}
