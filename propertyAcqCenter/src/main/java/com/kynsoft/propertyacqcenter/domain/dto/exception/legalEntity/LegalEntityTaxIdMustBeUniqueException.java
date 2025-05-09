package com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity;

public class LegalEntityTaxIdMustBeUniqueException extends RuntimeException {
    public LegalEntityTaxIdMustBeUniqueException(String taxId) {
        super("The taxId: " + taxId + " already exists. ");
    }
}
