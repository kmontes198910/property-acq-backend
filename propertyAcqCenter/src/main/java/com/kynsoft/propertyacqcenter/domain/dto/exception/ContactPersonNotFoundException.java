package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class ContactPersonNotFoundException extends RuntimeException {
    public ContactPersonNotFoundException(String id, String property) {
        super("Contact Person with " + property +": " + id + " not found");
    }
    }