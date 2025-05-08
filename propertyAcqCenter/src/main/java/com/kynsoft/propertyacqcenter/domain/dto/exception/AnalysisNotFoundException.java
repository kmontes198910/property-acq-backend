package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class AnalysisNotFoundException extends RuntimeException {
    public AnalysisNotFoundException(UUID id) {
        super("Analysis with ID " + id + " not found");
    }
}
