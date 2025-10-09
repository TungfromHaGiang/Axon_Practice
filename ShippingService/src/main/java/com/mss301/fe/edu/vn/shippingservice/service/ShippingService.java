package com.mss301.fe.edu.vn.shippingservice.service;

import com.mss301.fe.edu.vn.shippingservice.entity.Shipping;

import java.util.List;

public interface ShippingService {
    List<Shipping> getShippings(String userId);
    List<Shipping> getAllShippings();
    Shipping completeShipping(String shippingId, Shipping shipping);
    Shipping cancelShipping(String shippingId, Shipping shipping);}
