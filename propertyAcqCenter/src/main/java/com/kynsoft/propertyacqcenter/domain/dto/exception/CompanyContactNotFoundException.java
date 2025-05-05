package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class CompanyContactNotFoundException extends RuntimeException {
    public CompanyContactNotFoundException(UUID id) {
        super("Company Contact with ID " + id + " not found.");
    }
}
