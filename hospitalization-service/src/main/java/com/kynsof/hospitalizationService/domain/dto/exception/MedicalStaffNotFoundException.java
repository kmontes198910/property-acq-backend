package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class MedicalStaffNotFoundException extends RuntimeException {
    public MedicalStaffNotFoundException(UUID id) {
        super("Medical Staff with ID " + id + " not found");
    }
}
