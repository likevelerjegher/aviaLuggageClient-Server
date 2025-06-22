package com.example.model;

public enum BaggageStatus {
    RECEIVED("Зарегистрирован"),
    SORTED_DEPARTURE("Отсортирован в пункте отправления"),
    IN_TRANSIT("В пути"),
    SORTED_ARRIVAL("Отсортирован в пункте прибытия"),
    REGISTERED("Получен");

    private final String displayName;

    BaggageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BaggageStatus fromDisplayName(String displayName) {
        for (BaggageStatus status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неизвестный статус: " + displayName);
    }
}

