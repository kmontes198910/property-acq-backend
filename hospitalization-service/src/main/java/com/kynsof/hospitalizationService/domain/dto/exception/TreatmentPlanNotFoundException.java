package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class TreatmentPlanNotFoundException extends RuntimeException {
    public TreatmentPlanNotFoundException(UUID id) {
        super("Treatment Plan with ID " + id + " not found");
    }
}
