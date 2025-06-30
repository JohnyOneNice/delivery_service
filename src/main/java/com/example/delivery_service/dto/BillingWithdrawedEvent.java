package com.example.delivery_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingWithdrawedEvent {
    private UUID orderId;
    private UUID userId;
    private int deliverySlotId;
    private LocalDate deliveryDate;
} 