package com.example.delivery_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class DeliveryRequest {
    private UUID orderId;
    private int deliverySlotId;
    private java.time.LocalDate deliveryDate;
}