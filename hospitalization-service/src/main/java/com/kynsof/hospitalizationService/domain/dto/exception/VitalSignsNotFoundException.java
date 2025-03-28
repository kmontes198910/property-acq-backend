package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class VitalSignsNotFoundException extends RuntimeException {
    public VitalSignsNotFoundException(UUID id) {
        super("Vital Signs with ID " + id + " not found");
    }
}
