package com.mss.axonorderservicecommon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private double price;
}
