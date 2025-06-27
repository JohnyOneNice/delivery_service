package com.example.delivery_service.model;

public enum DeliverySlot {
    SLOT_1(1, "09:00-13:00"),
    SLOT_2(2, "13:00-17:00"),
    SLOT_3(3, "17:00-22:00");

    private final int id;
    private final String description;

    DeliverySlot(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static DeliverySlot fromId(int id) {
        for (DeliverySlot slot : values()) {
            if (slot.id == id) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Invalid slot id: " + id);
    }
} 