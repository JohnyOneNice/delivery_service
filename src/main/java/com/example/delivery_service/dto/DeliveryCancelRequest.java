package com.example.delivery_service.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCancelRequest {
    private UUID orderId;
}