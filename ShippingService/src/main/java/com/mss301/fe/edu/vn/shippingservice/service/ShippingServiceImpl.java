package com.mss301.fe.edu.vn.shippingservice.service;

import com.mss301.fe.edu.vn.shippingservice.entity.Shipping;
import com.mss301.fe.edu.vn.shippingservice.repository.ShippingRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public List<Shipping> getShippings(String userId) {
        return shippingRepository.findByUserId(userId);
    }

    @Override
    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }

    @Override
    public Shipping completeShipping(String shippingId, Shipping shipping) {
        CompleteShippingCommand command = new CompleteShippingCommand(
                shippingId,
                shipping.getUserId(),
                shipping.getStatus()
        );

        Shipping updatedShipping = shippingRepository.getReferenceById(shippingId);
        updatedShipping.setStatus(ShippingStatus.DELIVERED.name());

        commandGateway.sendAndWait(command);
        return updatedShipping;
    }

    @Override
    public Shipping cancelShipping(String shippingId, Shipping shipping) {
        // TODO: Call Payment Service để lấy paymentId thực tế
        String paymentId = "shipping.getPaymentId()"; // Giả lập

        CancelShippingCommand command = new CancelShippingCommand(
                shippingId,
                shipping.getUserId(),
                paymentId,
                shipping.getStatus()
        );

        Shipping updatedShipping = shippingRepository.getReferenceById(shippingId);
        updatedShipping.setStatus(ShippingStatus.CANCELLED.name());

        commandGateway.sendAndWait(command);
        return updatedShipping;
    }
}
