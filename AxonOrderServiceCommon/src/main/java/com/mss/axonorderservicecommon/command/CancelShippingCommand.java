package com.mss.axonorderservicecommon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelShippingCommand {
    private String shippingId;
    private String orderId;
    private String paymentId;
    private String status;
}
