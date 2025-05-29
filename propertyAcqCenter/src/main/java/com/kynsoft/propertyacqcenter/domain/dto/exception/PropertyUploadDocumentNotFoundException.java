package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class PropertyUploadDocumentNotFoundException extends RuntimeException {
    public PropertyUploadDocumentNotFoundException(UUID id) {
        super("Property Document with ID " + id + " not found.");
    }
}
