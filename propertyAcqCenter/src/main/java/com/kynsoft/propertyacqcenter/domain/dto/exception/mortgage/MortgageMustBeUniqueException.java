package com.kynsoft.propertyacqcenter.domain.dto.exception.mortgage;

public class MortgageMustBeUniqueException extends RuntimeException {
    public MortgageMustBeUniqueException(String email) {
        super("A mortgage already exists for this property.");
    }
}
