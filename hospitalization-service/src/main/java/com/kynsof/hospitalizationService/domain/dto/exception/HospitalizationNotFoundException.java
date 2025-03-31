package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class HospitalizationNotFoundException extends RuntimeException {
    public HospitalizationNotFoundException(UUID id) {
        super("Hospitalization with ID " + id + " not found");
    }
}
