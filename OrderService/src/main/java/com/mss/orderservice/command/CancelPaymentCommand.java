package com.mss.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentCommand {
    private String paymentId;
    private String orderId;
    private String userId;
    private double amount;
}
