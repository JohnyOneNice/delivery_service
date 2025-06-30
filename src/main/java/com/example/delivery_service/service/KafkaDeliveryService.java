package com.example.delivery_service.service;

import com.example.delivery_service.dto.DeliveryRequest;
import com.example.delivery_service.model.DeliveryTask;
import com.example.delivery_service.dto.BillingWithdrawedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDeliveryService {
    private final DeliveryService deliveryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${delivery.ok.topic:delivery-ok}")
    private String deliveryOkTopic;

    @KafkaListener(topics = "billing-withdrawed", groupId = "delivery-service")
    public void handleBillingWithdrawed(BillingWithdrawedEvent event) {
        try {
            log.info("Получено событие billing-withdrawed: {}", event);
            UUID orderId = event.getOrderId();
            UUID userId = event.getUserId();
            int deliverySlotId = event.getDeliverySlotId();
            java.time.LocalDate deliveryDate = event.getDeliveryDate();
            DeliveryRequest req = DeliveryRequest.builder()
                .orderId(orderId)
                .deliverySlotId(deliverySlotId)
                .deliveryDate(deliveryDate)
                .build();
            DeliveryTask task = deliveryService.createDelivery(req);
            publishDeliveryOk(orderId, userId);
        } catch (Exception e) {
            log.error("Ошибка обработки события billing-withdrawed", e);
        }
    }

    public void publishDeliveryOk(UUID orderId, UUID userId) {
        Map<String, Object> event = Map.of(
                "orderId", orderId.toString(),
                "userId", userId.toString()
        );
        log.info("Публикую событие в {}: {}", deliveryOkTopic, event);
        kafkaTemplate.send(deliveryOkTopic, event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Сообщение успешно отправлено в {}: {}", deliveryOkTopic, event);
                } else {
                    log.error("Ошибка отправки сообщения в {}", deliveryOkTopic, ex);
                }
            });
    }
} 