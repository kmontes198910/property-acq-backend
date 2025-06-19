package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(UUID id) {
        super("Role with ID " + id + " not found.");
    }
}
