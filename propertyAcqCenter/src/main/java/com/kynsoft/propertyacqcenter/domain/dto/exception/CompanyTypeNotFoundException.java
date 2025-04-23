package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class CompanyTypeNotFoundException extends RuntimeException {
    public CompanyTypeNotFoundException(UUID id) {
        super("Company Type with ID " + id + " not found.");
    }
}
