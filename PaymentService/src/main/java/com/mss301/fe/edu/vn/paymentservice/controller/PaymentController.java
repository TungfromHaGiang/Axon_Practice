package com.mss301.fe.edu.vn.paymentservice.controller;

import com.mss301.fe.edu.vn.paymentservice.entity.Payment;
import com.mss301.fe.edu.vn.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<Payment>> getPayment(@PathVariable("orderId") String orderId) {
        List<Payment> payments = paymentService.getPayments(orderId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayment() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}
