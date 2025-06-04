package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class ExpensesNotFoundException extends RuntimeException {
    public ExpensesNotFoundException(UUID id) {
        super("Expenses with ID " + id + " not found.");
    }
}
