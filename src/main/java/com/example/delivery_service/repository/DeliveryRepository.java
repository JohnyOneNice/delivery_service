package com.example.delivery_service.repository;

import com.example.delivery_service.model.DeliveryTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryTask, UUID> {
    boolean existsByOrderId(UUID orderId);
}