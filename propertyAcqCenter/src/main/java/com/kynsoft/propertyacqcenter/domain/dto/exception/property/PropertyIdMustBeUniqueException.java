package com.kynsoft.propertyacqcenter.domain.dto.exception.property;

public class PropertyIdMustBeUniqueException extends RuntimeException {
    public PropertyIdMustBeUniqueException(String id) {
        super("The property id: " + id + ", already exists. ");
    }
}
