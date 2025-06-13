package com.kynsoft.propertyacqcenter.domain.dto.exception.income;

public class IncomeMustBeUniqueException extends RuntimeException {
    public IncomeMustBeUniqueException(String email) {
        super("A income already exists for this property.");
    }
}
