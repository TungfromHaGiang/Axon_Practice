package com.mss301.fe.edu.vn.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id", nullable = false, length = 100)
    private String paymentId;

    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "status", nullable = false, length = 50)
    private String status;
}