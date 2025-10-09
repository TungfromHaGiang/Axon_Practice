package com.mss.axonorderservicecommon.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentCompletedEvent {
    private String paymentId;
    private String orderId;
    private String userId;
    private double price;
    private String status = "SUCCESS";
}
