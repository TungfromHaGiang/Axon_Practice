package com.mss301.fe.edu.vn.shippingservice.entity;

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
@Table(name = "shippings")
public class Shipping {

    @Id
    @Column(name = "shipping_id", nullable = false, length = 100)
    private String shippingId;

    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "status", nullable = false, length = 50)
    private String status;
}
