package com.kynsoft.propertyacqcenter.domain.dto.exception.propertyUploadDocument;

public class PropertyNotNullException extends RuntimeException {
    public PropertyNotNullException() {
        super("The document must be assigned to a property.");
    }
}
