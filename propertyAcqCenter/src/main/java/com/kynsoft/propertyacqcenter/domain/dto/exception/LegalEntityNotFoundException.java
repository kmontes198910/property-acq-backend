package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class LegalEntityNotFoundException extends RuntimeException {
    public LegalEntityNotFoundException(String id, String property) {
        super("Legal Entity with " + property +": " + id + " not found.");
    }
}
