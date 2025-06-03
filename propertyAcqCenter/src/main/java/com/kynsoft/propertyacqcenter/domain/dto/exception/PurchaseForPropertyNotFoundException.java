package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class PurchaseForPropertyNotFoundException extends RuntimeException {
    public PurchaseForPropertyNotFoundException() {
        super("Purchase with ID not found.");
    }
}
