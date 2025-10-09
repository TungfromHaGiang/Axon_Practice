package com.mss301.fe.edu.vn.paymentservice.aggregate;

import com.mss301.orderservice.command.CancelPaymentCommand;
import com.mss301.orderservice.command.ProcessPaymentCommand;
import com.mss301.orderservice.common.PaymentStatus;
import com.mss301.orderservice.event.PaymentCancelledEvent;
import com.mss301.orderservice.event.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String userId;
    private double amount;
    private String status;

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand command) {
        String status = simulatePayment(command);
        PaymentProcessedEvent event = new PaymentProcessedEvent(
                command.getPaymentId(),
                command.getOrderId(),
                command.getUserId(),
                command.getAmount(),
                status
        );
        AggregateLifecycle.apply(event);

        System.out.println("PaymentAggregate - Payment processed for paymentId: "
                + command.getPaymentId() + " with status: " + status);
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.amount = event.getAmount();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CancelPaymentCommand command) {
        PaymentCancelledEvent event = new PaymentCancelledEvent(
                command.getPaymentId(),
                command.getOrderId(),
                "CANCELLED"
        );
        AggregateLifecycle.apply(event);
        System.out.println("PaymentAggregate - Payment cancelled for paymentId: " + command.getPaymentId());
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent event) {
        this.status = event.getStatus();
    }

    private String simulatePayment(ProcessPaymentCommand command) {
        // Giả lập thanh toán: ở thực tế có thể gọi API thanh toán bên thứ 3
        if (command.getAmount() > 0) {
            return PaymentStatus.SUCCESS.name();
        } else {
            return PaymentStatus.FAILED.name();
        }
    }
}
