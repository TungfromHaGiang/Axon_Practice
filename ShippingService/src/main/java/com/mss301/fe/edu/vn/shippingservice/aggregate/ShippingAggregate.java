package com.mss301.fe.edu.vn.shippingservice.aggregate;

import com.mss.axonorderservicecommon.command.CancelShippingCommand;
import com.mss.axonorderservicecommon.command.ProcessShippingCommand;
import com.mss.axonorderservicecommon.common.ShippingStatus;
import com.mss.axonorderservicecommon.event.ShippingCancelledEvent;
import com.mss.axonorderservicecommon.event.ShippingProcessedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;
    private String orderId;
    private String userId;
    private double amount;
    private String status;

    /**
     * Command Handler cho việc xử lý vận chuyển
     */
    @CommandHandler
    public ShippingAggregate(ProcessShippingCommand command) {
        String status = simulateShipping(command);

        ShippingProcessedEvent event = new ShippingProcessedEvent(
                command.getShippingId(),
                command.getOrderId(),
                command.getUserId(),
                command.getPaymentId(),
                command.getAmount(),
                status
        );

        // Kích hoạt Event
        AggregateLifecycle.apply(event);
        System.out.println("ShippingAggregate - Shipping processed for shippingId: "
                + command.getShippingId() + " with status: " + status);
    }

    /**
     * Event Sourcing Handler cho ShippingProcessedEvent
     */
    @EventSourcingHandler
    public void on(ShippingProcessedEvent event) {
        this.shippingId = event.getShippingId();
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.amount = event.getAmount();
        this.status = event.getStatus();

        System.out.println("ShippingAggregate - Event sourcing applied for shippingId: "
                + event.getShippingId());
    }

    /**
     * Giả lập quá trình vận chuyển (mô phỏng tương tác với bên vận chuyển)
     */
    private String simulateShipping(ProcessShippingCommand command) {
        System.out.println("simulateShipping: Processing shipping for userId: " + command.getUserId());
        // Giả lập thất bại nếu userId = "out-of-area"
        return "out-of-area".equals(command.getUserId())
                ? ShippingStatus.FAILED.name()
                : ShippingStatus.DELIVERED.name();
    }

    @CommandHandler
    public void handle(CancelShippingCommand command) {
        ShippingCancelledEvent event = new ShippingCancelledEvent(
                command.getShippingId(),
                command.getOrderId(),
                command.getPaymentId(),
                ShippingStatus.CANCELLED.name()
        );

        // Logic hủy đơn hàng
        AggregateLifecycle.apply(event);

        System.out.println("ShippingAggregate - Shipping cancelled for shippingId: " + command.getShippingId());
    }

    @EventSourcingHandler
    public void on(ShippingCancelledEvent event) {
        this.status = event.getStatus();
        System.out.println("ShippingAggregate - Status updated to CANCELLED for shippingId: " + event.getShippingId());
    }
}