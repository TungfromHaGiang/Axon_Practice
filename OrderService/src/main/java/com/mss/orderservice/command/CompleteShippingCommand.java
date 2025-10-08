package com.mss.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteShippingCommand {
    @TargetAggregateIdentifier
    private String shippingId;  
    private String orderId;   
    private String status;
}
