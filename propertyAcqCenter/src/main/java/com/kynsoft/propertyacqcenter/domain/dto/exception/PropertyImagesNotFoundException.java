package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class PropertyImagesNotFoundException extends RuntimeException {
    public PropertyImagesNotFoundException(UUID id) {
        super("Property Images with ID " + id + " not found.");
    }
}
