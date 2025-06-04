package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class PurchaseForPropertyNotFoundException extends RuntimeException {
    public PurchaseForPropertyNotFoundException() {
        super("Purchase with ID not found.");
    }
}
