package com.mss.orderservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class OrderRequest {
    private String orderId;
    private String userId;
    private double price;
    private int quantity;
    private String productId;
}
