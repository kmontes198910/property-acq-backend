package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class HospitalDischargeSummaryNotFoundException extends RuntimeException {
    public HospitalDischargeSummaryNotFoundException(UUID id) {
        super("Hospital Discharge Summary with ID " + id + " not found");
    }
}
