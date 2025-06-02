package com.kynsoft.cirugia.domain.dto.exception;

import java.util.UUID;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(UUID id) {
        super("Patient with ID " + id + " not found.");
    }
}
