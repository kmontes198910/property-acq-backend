package com.kynsoft.propertyacqcenter.domain.dto.exception.purchase;

public class PurchaseMustBeUniqueException extends RuntimeException {
    public PurchaseMustBeUniqueException(String email) {
        super("A purchase already exists for this property.");
    }
}
