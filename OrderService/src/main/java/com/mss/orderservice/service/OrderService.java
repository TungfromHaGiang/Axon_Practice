package com.mss.orderservice.service;

import com.mss.orderservice.request.OrderRequest;

public interface OrderService {
    OrderRequest createOrder(OrderRequest request);
}
