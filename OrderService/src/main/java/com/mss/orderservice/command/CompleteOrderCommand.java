package com.mss.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private double price;
    private String status = "COMPLETED";
}
