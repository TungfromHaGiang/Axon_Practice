package com.mss301.fe.edu.vn.paymentservice.service;

import com.mss301.fe.edu.vn.paymentservice.entity.Payment;
import com.mss301.fe.edu.vn.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public List<Payment> getPayments(String userId) {
        return paymentRepository.findByUserId(userId);
    }
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    @Override
    public Payment getPaymentById(String paymentId) {
        return paymentRepository.getReferenceById(paymentId);
    }
}
