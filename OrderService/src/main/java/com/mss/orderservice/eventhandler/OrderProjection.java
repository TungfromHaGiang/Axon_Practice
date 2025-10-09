package com.mss.orderservice.eventhandler;

import com.mss.axonorderservicecommon.event.OrderCancelledEvent;
import com.mss.axonorderservicecommon.event.OrderCompletedEvent;
import com.mss.axonorderservicecommon.event.OrderCreatedEvent;
import com.mss.orderservice.entity.Order;

import com.mss.orderservice.repository.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderProjection {
    @Autowired
    private OrderRepository orderRepository;


    @EventHandler
    public void on(OrderCreatedEvent event) {
        // Cập nhật read model (database)
        Order order = new Order();
        order.setOrderId(event.getOrderId());
        order.setUserId(event.getUserId());
        order.setProductId(event.getProductId());
        order.setQuantity(event.getQuantity());
        order.setPrice(event.getPrice());
        order.setStatus(event.getStatus());

        orderRepository.save(order);
        System.out.println("Event received (OrderCreatedEvent): " + event);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElse(null);

        if (order != null) {
            order.setStatus(event.getStatus());
            orderRepository.save(order);
            System.out.println("Order status updated (Completed): " + event);
        } else {
            System.out.println("Order not found for OrderCompletedEvent: " + event);
        }
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElse(null);

        if (order != null) {
            order.setStatus(event.getStatus());
            orderRepository.save(order);
            System.out.println("❌ Order status updated (Cancelled): " + event);
        } else {
            System.out.println("⚠️ Order not found for OrderCancelledEvent: " + event);
        }
    }
}
