package com.mss.axonorderservicecommon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProcessShippingCommand {


    private String shippingId;
    private String orderId;
    private String userId;
    private String paymentId;
    private double amount;
}
