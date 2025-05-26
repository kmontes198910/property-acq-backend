package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class PropertyDocumentNotFoundException extends RuntimeException {
    public PropertyDocumentNotFoundException(UUID id) {
        super("Property Document with ID " + id + " not found.");
    }
}
