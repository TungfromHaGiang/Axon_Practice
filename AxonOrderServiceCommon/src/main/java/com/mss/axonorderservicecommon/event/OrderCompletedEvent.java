package com.mss.axonorderservicecommon.event;

import com.mss.orderservice.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCompletedEvent {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private double price;
    private String status = OrderStatus.COMPLETED.name();
}
