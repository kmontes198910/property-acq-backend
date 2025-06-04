package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class ExpresesForPropertyNotFoundException extends RuntimeException {
    public ExpresesForPropertyNotFoundException() {
        super("Expreses with ID not found.");
    }
}
