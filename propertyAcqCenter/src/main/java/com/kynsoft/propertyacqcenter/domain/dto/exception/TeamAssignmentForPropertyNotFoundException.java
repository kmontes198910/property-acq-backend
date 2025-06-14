package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class TeamAssignmentForPropertyNotFoundException extends RuntimeException {
    public TeamAssignmentForPropertyNotFoundException() {
        super("Team Assignment with not found.");
    }
}
