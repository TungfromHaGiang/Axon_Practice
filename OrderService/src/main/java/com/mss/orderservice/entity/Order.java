package com.mss.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_id", nullable = false, length = 100)
    private String productId;
}
