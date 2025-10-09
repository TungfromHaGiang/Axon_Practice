package com.mss.orderservice.aggregate;

import com.mss.axonorderservicecommon.command.CancelOrderCommand;
import com.mss.axonorderservicecommon.command.CompleteOrderCommand;
import com.mss.axonorderservicecommon.command.CreateOrderCommand;
import com.mss.axonorderservicecommon.common.OrderStatus;
import com.mss.axonorderservicecommon.event.OrderCancelledEvent;
import com.mss.axonorderservicecommon.event.OrderCompletedEvent;
import com.mss.axonorderservicecommon.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.springframework.beans.BeanUtils;

public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;

    private String userId;
    private String productId;
    private int quantity;
    private double price;
    private String orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        // Thiết lập trạng thái ban đầu của Order
        orderCreatedEvent.setStatus(OrderStatus.ORDERED.name());
        this.orderStatus = OrderStatus.ORDERED.name();

        // Áp dụng event để thay đổi state
        AggregateLifecycle.apply(orderCreatedEvent);
    }
    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.userId = orderCreatedEvent.getUserId();
        this.productId = orderCreatedEvent.getProductId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.price = orderCreatedEvent.getPrice();
        this.orderStatus = orderCreatedEvent.getStatus();

        System.out.println("Order " + orderCreatedEvent.getOrderId() + " created successfully.");
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        OrderCancelledEvent event = new OrderCancelledEvent();
        event.setOrderId(command.getOrderId());
        event.setUserId(command.getUserId());
        event.setStatus(OrderStatus.CANCELLED.name());

        AggregateLifecycle.apply(event);
        System.out.println("❌ Order " + command.getOrderId() + " cancelled.");
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.orderStatus = event.getStatus();
        System.out.println("Order status updated to CANCELLED for OrderID: " + event.getOrderId());
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command) {
        OrderCompletedEvent event = new OrderCompletedEvent();
        event.setOrderId(command.getOrderId());
        event.setUserId(userId);
        event.setStatus(OrderStatus.COMPLETED.name());

        AggregateLifecycle.apply(event);
        System.out.println("✅ Order " + command.getOrderId() + " completed.");
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.orderStatus = event.getStatus();
        System.out.println("OrderStatus: " + event.getStatus() + " for OrderID: " + event.getOrderId());
    }
}
