package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class DocumentTypeNotFoundException extends RuntimeException {
    public DocumentTypeNotFoundException(UUID id) {
        super("Document Type with ID " + id + " not found.");
    }
}
