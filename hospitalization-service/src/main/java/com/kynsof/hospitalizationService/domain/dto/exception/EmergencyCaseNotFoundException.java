package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class EmergencyCaseNotFoundException extends RuntimeException {
    public EmergencyCaseNotFoundException(UUID id) {
        super("Emergency Case with ID " + id + " not found");
    }
}
