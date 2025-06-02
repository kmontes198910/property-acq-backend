package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class InsuranceBrokerNotFoundException extends RuntimeException {
    public InsuranceBrokerNotFoundException(UUID id) {
        super("Insurance Broker with ID " + id + " not found.");
    }
}
