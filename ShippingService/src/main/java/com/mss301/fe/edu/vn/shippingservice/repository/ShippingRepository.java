package com.mss301.fe.edu.vn.shippingservice.repository;

import com.mss301.fe.edu.vn.shippingservice.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, String> {
    List<Shipping> findByUserId(String userId);
}
