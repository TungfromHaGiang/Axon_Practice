package com.mss.orderservice.service.impl;

import com.mss.orderservice.command.CreateOrderCommand;
import com.mss.orderservice.common.OrderStatus;
import com.mss.orderservice.entity.Order;
import com.mss.orderservice.repository.OrderRepository;
import com.mss.orderservice.request.OrderRequest;
import com.mss.orderservice.service.OrderService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderRequest createOrder(OrderRequest orderRequest) {
        // 🔹 Tạo orderId ngẫu nhiên
        String orderId = java.util.UUID.randomUUID().toString();
        orderRequest.setOrderId(orderId);

        // 🔹 Gửi CreateOrderCommand qua Axon
        CreateOrderCommand command = new CreateOrderCommand(
                orderId,
                orderRequest.getUserId(),
                orderRequest.getProductId(),
                orderRequest.getQuantity(),
                orderRequest.getPrice()
        );

        // 🔹 Lưu order vào database (nếu cần)
        Order order = new Order();
        order.setOrderId(orderRequest.getOrderId());
        order.setUserId(orderRequest.getUserId());
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setStatus(OrderStatus.CREATED.name());

        orderRepository.save(order);

        // 🔹 Gửi command đến Axon để xử lý business logic
        commandGateway.sendAndWait(command);

        System.out.println("✅ Order created: " + orderRequest);
        return orderRequest;
    }
}
