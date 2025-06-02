package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(String id) {
        super("Property with ID " + id + " not found.");
    }
}
