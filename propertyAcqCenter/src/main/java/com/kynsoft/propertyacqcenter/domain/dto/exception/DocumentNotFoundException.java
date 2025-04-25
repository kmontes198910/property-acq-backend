package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(UUID id) {
        super("Document with ID " + id + " not found.");
    }
}
