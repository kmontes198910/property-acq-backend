package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class CompanyAddressNotFoundException extends RuntimeException {
    public CompanyAddressNotFoundException(UUID id) {
        super("Company Address with ID " + id + " not found.");
    }
}
