package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class InsuranceNotFoundException extends RuntimeException {
    public InsuranceNotFoundException(UUID id) {
        super("Insurance with ID " + id + " not found");
    }
}
