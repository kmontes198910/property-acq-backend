package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(UUID id) {
        super("Address with ID " + id + " not found.");
    }
}
