package com.kynsoft.cirugia.domain.dto.exception;

import java.util.UUID;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(UUID id) {
        super("Business with ID " + id + " not found");
    }
}
