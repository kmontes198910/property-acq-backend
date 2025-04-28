package com.kynsoft.propertyacqcenter.domain.enums;

public enum PropertyType {
    SINGLE_FAMILY("Single Family"),
    MULTI_FAMILY("Multi Family"),
    CONDO("Condo"),
    TOWNHOUSE("Townhouse"),
    LAND("Land"),
    APARTMENT("Apartment"),
    MANUFACTURED("Manufactured"),
    OTHER("Other");

    private final String value;

    PropertyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PropertyType fromString(String value) {
        for (PropertyType type : PropertyType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
