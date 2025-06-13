package com.kynsoft.propertyacqcenter.domain.dto.exception.sales;

public class SalesMustBeUniqueException extends RuntimeException {
    public SalesMustBeUniqueException(String email) {
        super("A sales already exists for this property.");
    }
}
