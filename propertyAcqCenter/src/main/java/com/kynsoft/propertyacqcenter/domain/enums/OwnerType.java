package com.kynsoft.propertyacqcenter.domain.enums;

public enum OwnerType {
    INDIVIDUAL("Individual"),
    ORGANIZATION("Organization");

    private final String value;

    OwnerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OwnerType fromString(String value) {
        for (OwnerType type : OwnerType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
