package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class AcquisitionDocumentNotFoundException extends RuntimeException {
    public AcquisitionDocumentNotFoundException(UUID id) {
        super("Acquisition Document with ID " + id + " not found.");
    }
}
