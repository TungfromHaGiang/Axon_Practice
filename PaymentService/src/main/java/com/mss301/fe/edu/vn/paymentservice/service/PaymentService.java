package com.mss301.fe.edu.vn.paymentservice.service;

import com.mss301.fe.edu.vn.paymentservice.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getPayments(String userId);
    List<Payment> getAllPayments();
    Payment getPaymentById(String paymentId);
}
