package com.mss.axonorderservicecommon.command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProcessPaymentCommand {
    private String paymentId;private String orderId;
    private String userId;
    private double amount;
}
