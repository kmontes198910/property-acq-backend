package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class TeamAssignmentNotFoundException extends RuntimeException {
    public TeamAssignmentNotFoundException(UUID id) {
        super("Team Assignment with ID " + id + " not found.");
    }
}
