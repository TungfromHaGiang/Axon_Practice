package com.mss.orderservice.saga;

import com.mss.axonorderservicecommon.command.CancelOrderCommand;
import com.mss.axonorderservicecommon.command.CancelPaymentCommand;
import com.mss.axonorderservicecommon.command.CompleteOrderCommand;
import com.mss.axonorderservicecommon.command.ProcessPaymentCommand;
import com.mss.axonorderservicecommon.command.ProcessShippingCommand;
import com.mss.axonorderservicecommon.common.OrderStatus;
import com.mss.axonorderservicecommon.common.PaymentStatus;
import com.mss.axonorderservicecommon.common.ShippingStatus;
import com.mss.axonorderservicecommon.event.OrderCreatedEvent;
import com.mss.axonorderservicecommon.event.PaymentProcessedEvent;
import com.mss.axonorderservicecommon.event.ShippingCancelledEvent;
import com.mss.axonorderservicecommon.event.ShippingProcessedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private String paymentId;
    private String shippingId;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        paymentId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("paymentId", paymentId);

        ProcessPaymentCommand command = new ProcessPaymentCommand(
                paymentId,
                event.getOrderId(),
                event.getUserId(),
                event.getPrice() * event.getQuantity()
        );

        System.out.println("OrderSaga - Processing payment for orderId: " + event.getOrderId());
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(PaymentProcessedEvent event) {
        paymentId = event.getPaymentId();

        // Handle payment processed event
        if (PaymentStatus.SUCCESS.name().equals(event.getStatus())) {
            // Proceed with shipping or other steps
            shippingId = UUID.randomUUID().toString();
            SagaLifecycle.associateWith("shippingId", shippingId);

            ProcessShippingCommand command = new ProcessShippingCommand(
                    shippingId,
                    event.getOrderId(),
                    event.getUserId(),
                    event.getPaymentId(),
                    event.getAmount()
            );

            commandGateway.send(command);
        } else {
            // Handle payment failure, possibly compensating actions
            compensateOrder(event.getOrderId());
        }
    }


    @SagaEventHandler(associationProperty = "shippingId")
    public void handleShippingCompleted(ShippingProcessedEvent event) {
        if (ShippingStatus.DELIVERED.name().equals(event.getStatus())) {
            // Shipping succeeded — Complete the order
            CompleteOrderCommand command = new CompleteOrderCommand();
            command.setOrderId(event.getOrderId());
            command.setStatus(OrderStatus.COMPLETED.name());

            commandGateway.send(command);
            SagaLifecycle.end(); // End saga after successful completion
        } else {
            //Handle shipping failure — perform compensating actions
            compensatePayment(event.getOrderId(), event.getPaymentId());
            compensateOrder(event.getOrderId());
        }
    }

    @SagaEventHandler(associationProperty = "shippingId")
    public void handleShippingCancelled(ShippingCancelledEvent event) {
        //Shipping cancelled — perform compensation
        compensatePayment(event.getOrderId(), event.getPaymentId());
        compensateOrder(event.getOrderId());
    }

    /**
     * Compensate order if shipping or payment fails
     */
    private void compensateOrder(String orderId) {
        CancelOrderCommand command = new CancelOrderCommand();
        command.setOrderId(orderId);

        commandGateway.send(command);
        SagaLifecycle.end(); // End saga after cancellation
    }

    /**
     * Compensate payment if order or shipping fails
     */
    private void compensatePayment(String orderId, String paymentId) {
        System.out.println("Compensating payment for orderId: " + orderId + ", paymentId: " + paymentId);

        CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand();
        cancelPaymentCommand.setPaymentId(paymentId);
        cancelPaymentCommand.setOrderId(orderId);

        commandGateway.send(cancelPaymentCommand);
    }

}
