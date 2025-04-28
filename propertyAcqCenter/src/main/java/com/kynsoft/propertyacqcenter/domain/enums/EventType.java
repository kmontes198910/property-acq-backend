package com.kynsoft.propertyacqcenter.domain.enums;

public enum EventType {
    SALE("Sale"),
    RENTAL("Rental");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EventType fromValue(String value) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getValue().equalsIgnoreCase(value)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
