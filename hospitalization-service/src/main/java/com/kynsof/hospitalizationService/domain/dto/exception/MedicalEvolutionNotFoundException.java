package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class MedicalEvolutionNotFoundException extends RuntimeException {
    public MedicalEvolutionNotFoundException(UUID id) {
        super("Medical Evolution with ID " + id + " not found");
    }
}
