package com.kynsoft.settings.domain.dto.exception;

import java.util.UUID;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(UUID id) {
        super("Address with ID " + id + " not found.");
    }
}
