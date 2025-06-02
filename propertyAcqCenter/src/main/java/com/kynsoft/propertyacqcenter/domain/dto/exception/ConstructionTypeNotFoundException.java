package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class ConstructionTypeNotFoundException extends RuntimeException {
    public ConstructionTypeNotFoundException(UUID id) {
        super("Construction Type with ID " + id + " not found");
    }
}
