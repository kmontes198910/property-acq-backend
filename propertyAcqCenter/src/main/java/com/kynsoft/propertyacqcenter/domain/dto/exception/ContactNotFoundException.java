package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String id, String property) {
        super("Contact with " + property +": " + id + " not found");
    }
    }