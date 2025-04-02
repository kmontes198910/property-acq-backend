package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class PrescribedMedicationNotFoundException extends RuntimeException {
    public PrescribedMedicationNotFoundException(UUID id) {
        super("Prescribed Medication with ID " + id + " not found");
    }
}
