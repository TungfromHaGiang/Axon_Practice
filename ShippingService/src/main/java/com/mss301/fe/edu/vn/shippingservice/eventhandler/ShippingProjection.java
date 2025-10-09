package com.mss301.fe.edu.vn.shippingservice.eventhandler;

import com.mss301.fe.edu.vn.shippingservice.entity.Shipping;
import com.mss301.fe.edu.vn.shippingservice.repository.ShippingRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShippingProjection {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private CommandGateway commandGateway;

    @EventHandler
    public void on(ShippingProcessedEvent event) {
        System.out.println("ShippingProcessedEvent received: " + event);
        Shipping shipping = new Shipping();
        shipping.setShippingId(event.getShippingId());
        shipping.setOrderId(event.getOrderId());
        shipping.setStatus(event.getStatus());
        shipping.setUserId(event.getUserId());
        shipping.setAmount(event.getAmount());
        shippingRepository.save(shipping);
    }

    @EventHandler
    public void on(ShippingCancelledEvent event) {
        System.out.println("ShippingCancelledEvent received: " + event);
        try {
            Shipping shipping = shippingRepository.getReferenceById(event.getShippingId());
            if (shipping != null) {
                shipping.setStatus(event.getStatus());
                shippingRepository.save(shipping);
            } else {
                System.out.println("Shipping not found for ID: " + event.getShippingId());
            }
        } catch (Exception e) {
            System.out.println("Error updating cancelled shipping: " + e.getMessage());
        }
    }

    @EventHandler
    public void on(ShippingCompletedEvent event) {
        System.out.println("ShippingCompletedEvent received: " + event);
        try {
            Shipping shipping = shippingRepository.getReferenceById(event.getShippingId());
            if (shipping != null) {
                shipping.setStatus(event.getStatus());
                shippingRepository.save(shipping);
            } else {
                System.out.println("Shipping not found for ID: " + event.getShippingId());
            }
        } catch (Exception e) {
            System.out.println("Error updating completed shipping: " + e.getMessage());
        }
    }
}
