package com.mss.orderservice.envent;

import com.mss.orderservice.common.OrderStatus;

public class OrderCompletedEvent {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private double price;
    private String status = OrderStatus.COMPLETED.name();
}
