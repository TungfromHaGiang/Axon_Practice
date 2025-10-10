package com.mss301.fe.edu.vn.shippingservice.controller;

import com.mss.axonorderservicecommon.common.ShippingStatus;
import com.mss301.fe.edu.vn.shippingservice.entity.Shipping;
import com.mss301.fe.edu.vn.shippingservice.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping
    public ResponseEntity<List<Shipping>> getAllShippings() {
        List<Shipping> shippings = shippingService.getAllShippings();
        return ResponseEntity.ok(shippings);
    }

    @PutMapping("/{shippingId}")
    public ResponseEntity<Shipping> completeShipping(
            @PathVariable("shippingId") String shippingId,
            @RequestBody Shipping shipping) {

        // Gọi service để hoàn tất giao hàng
        shipping.setStatus(ShippingStatus.DELIVERED.name());
        Shipping updatedShipping = shippingService.completeShipping(shippingId, shipping);
        return ResponseEntity.ok(updatedShipping);
    }

    @PutMapping("/cancel/{shippingId}")
    public ResponseEntity<Shipping> cancelShipping(
            @PathVariable("shippingId") String shippingId,
            @RequestBody Shipping shipping) {

        // Gọi service để hủy giao hàng
        shipping.setStatus(ShippingStatus.CANCELLED.name());
        Shipping updatedShipping = shippingService.cancelShipping(shippingId, shipping);
        return ResponseEntity.ok(updatedShipping);
    }
}
