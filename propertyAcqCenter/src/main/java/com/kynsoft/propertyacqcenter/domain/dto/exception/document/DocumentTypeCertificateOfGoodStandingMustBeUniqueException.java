package com.kynsoft.propertyacqcenter.domain.dto.exception.document;

public class DocumentTypeCertificateOfGoodStandingMustBeUniqueException extends RuntimeException {
    public DocumentTypeCertificateOfGoodStandingMustBeUniqueException() {
        super("A Certificate of Good Standing already exists for this legal entity.");
    }
}
