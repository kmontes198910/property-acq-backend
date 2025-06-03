package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class IncomeForPropertyNotFoundException extends RuntimeException {
    public IncomeForPropertyNotFoundException() {
        super("Income with ID not found.");
    }
}
