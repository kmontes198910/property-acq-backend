package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class NotDeleteException extends RuntimeException {
    public NotDeleteException() {
        super("Element cannot be deleted has a related element.");
    }
}
