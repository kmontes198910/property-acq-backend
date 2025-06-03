package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException(UUID id) {
        super("Purchase with ID " + id + " not found.");
    }
}
