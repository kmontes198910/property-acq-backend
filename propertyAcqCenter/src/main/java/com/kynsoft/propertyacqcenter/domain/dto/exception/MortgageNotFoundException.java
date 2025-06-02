package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class MortgageNotFoundException extends RuntimeException {
    public MortgageNotFoundException(UUID id) {
        super("Mortgage with ID " + id + " not found.");
    }
}
