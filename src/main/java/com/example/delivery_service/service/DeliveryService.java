package com.example.delivery_service.service;

import com.example.delivery_service.dto.DeliveryRequest;
import com.example.delivery_service.model.DeliveryTask;
import com.example.delivery_service.model.DeliverySlot;
import com.example.delivery_service.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryTask createDelivery(DeliveryRequest request) {
        if (repository.existsByOrderId(request.getOrderId())) {
            throw new RuntimeException("Уже существует доставка для заказа");
        }
        // Проверка валидности id слота
        DeliverySlot slot = DeliverySlot.fromId(request.getDeliverySlotId());
        DeliveryTask task = DeliveryTask.builder()
                .orderId(request.getOrderId())
                .deliverySlotId(slot.getId())
                .deliveryDate(request.getDeliveryDate())
                .build();
        return repository.save(task);
    }

    // ✅ Компенсационное действие для Saga: отмена доставки
    public void cancel(UUID orderId) {
        Optional<DeliveryTask> task = repository.findByOrderId(orderId);
        task.ifPresent(repository::delete);
    }
}