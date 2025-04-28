package com.kynsoft.propertyacqcenter.domain.enums;

import lombok.Getter;

@Getter
public enum ListingType {
    STANDARD("Standard"),
    NEW_CONSTRUCTION("New Construction"),
    SHORT_SALE("Short Sale"),
    FORECLOSURE("Foreclosure");

    private final String value;

    ListingType(String value) {
        this.value = value;
    }

    public static ListingType fromString(String value) {
        for (ListingType type : ListingType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
