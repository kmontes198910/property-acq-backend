package com.kynsoft.cirugia.domain.dto.exception;

import java.util.UUID;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(UUID id) {
        super("Doctor with ID " + id + " not found.");
    }
}
