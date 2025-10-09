package com.mss301.fe.edu.vn.paymentservice.eventhandler;

import com.mss301.fe.edu.vn.paymentservice.entity.Payment;
import com.mss301.fe.edu.vn.paymentservice.repository.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentProjection {

    @Autowired
    private PaymentRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        System.out.println("PaymentProcessedEvent received: " + event);

        // Update read model
        Payment payment = new Payment();
        payment.setPaymentId(event.getPaymentId());
        payment.setOrderId(event.getOrderId());
        payment.setStatus(event.getStatus());
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount());

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        System.out.println("PaymentCancelledEvent received: " + event);

        // Update read model
        Payment payment = paymentRepository.findById(event.getPaymentId()).orElse(null);
        if (payment != null) {
            payment.setStatus(event.getStatus());
            paymentRepository.save(payment);
        } else {
            System.out.println("Payment not found for ID: " + event.getPaymentId());
        }
    }

    @EventHandler
    public void on(PaymentCompletedEvent event) {
        System.out.println("PaymentCompletedEvent received: " + event);

        // Update read model
        Payment payment = paymentRepository.findById(event.getPaymentId()).orElse(null);
        if (payment != null) {
            payment.setStatus(event.getStatus());
            paymentRepository.save(payment);
        } else {
            System.out.println("Payment not found for ID: " + event.getPaymentId());
        }
    }
}
