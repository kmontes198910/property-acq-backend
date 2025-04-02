package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class BedNotFoundException extends RuntimeException {
    public BedNotFoundException(UUID id) {
        super("Bed with ID " + id + " not found");
    }
}
