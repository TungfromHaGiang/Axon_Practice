package com.mss.axonorderservicecommon.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShippingProcessedEvent {
    private String shippingId;
    private String orderId;
    private String userId;
    private String paymentId;
    private double amount;
    private String status;
}
