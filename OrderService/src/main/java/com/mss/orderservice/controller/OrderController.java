package com.mss.orderservice.controller;

import com.mss.orderservice.request.OrderRequest;
import com.mss.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("Received order request: " + orderRequest);

        OrderRequest createdOrder = orderService.createOrder(orderRequest);

        System.out.println("Order created successfully: " + createdOrder);
        return ResponseEntity.ok(createdOrder);
    }
}