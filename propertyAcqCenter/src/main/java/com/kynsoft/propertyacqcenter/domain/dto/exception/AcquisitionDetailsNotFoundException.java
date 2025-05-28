package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class AcquisitionDetailsNotFoundException extends RuntimeException {
    public AcquisitionDetailsNotFoundException(UUID id) {
        super("Acquisition Details with ID " + id + " not found.");
    }
}
