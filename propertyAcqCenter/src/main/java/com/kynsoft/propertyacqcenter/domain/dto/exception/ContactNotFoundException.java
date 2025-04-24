package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String id, String property) {
        super("Employee with " + property +": " + id + " not found");
    }
    }