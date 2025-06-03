package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class IncomeNotFoundException extends RuntimeException {
    public IncomeNotFoundException(UUID id) {
        super("Income with ID " + id + " not found.");
    }
}
