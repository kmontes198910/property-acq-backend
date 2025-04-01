package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class MedicalPrescriptionDtoNotFoundException extends RuntimeException {
    public MedicalPrescriptionDtoNotFoundException(UUID id) {
        super("Medical Prescription with ID " + id + " not found");
    }
}
