package com.example.delivery_service.controller;

import com.example.delivery_service.dto.DeliveryRequest;
import com.example.delivery_service.model.DeliveryTask;
import com.example.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryTask> reserve(@RequestBody DeliveryRequest request) {
        try {
            return ResponseEntity.ok(deliveryService.createDelivery(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}