package com.mss.axonorderservicecommon.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShippingCancelledEvent {
    private String shippingId;
    private String orderId;
    private String paymentId;
    private String status;
}
