package com.kynsoft.propertyacqcenter.domain.dto.exception.documentType;

public class DocumentTypeCodeMustBeUniqueException extends RuntimeException {
    public DocumentTypeCodeMustBeUniqueException(String code) {
        super("The code: " + code + " already exists. ");
    }
}
