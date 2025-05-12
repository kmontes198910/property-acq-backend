package com.kynsoft.propertyacqcenter.domain.dto.exception.contact;

public class LegalEntityNotNullException extends RuntimeException {
    public LegalEntityNotNullException() {
        super("Each contact must be assigned to a valid Legal Entity.");
    }
}
