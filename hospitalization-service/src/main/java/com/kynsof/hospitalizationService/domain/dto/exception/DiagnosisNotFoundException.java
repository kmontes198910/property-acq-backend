package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class DiagnosisNotFoundException extends RuntimeException {
    public DiagnosisNotFoundException(UUID id) {
        super("Diagnosis with ID " + id + " not found");
    }
}
