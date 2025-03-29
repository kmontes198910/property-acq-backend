package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class EmergencyDischargeNotFoundException extends RuntimeException {
    public EmergencyDischargeNotFoundException(UUID id) {
        super("Emergency Discharge with ID " + id + " not found");
    }
}
