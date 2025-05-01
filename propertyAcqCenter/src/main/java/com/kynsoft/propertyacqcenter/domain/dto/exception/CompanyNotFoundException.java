package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String id, String property) {
        super("Contact Person with " + property +": " + id + " not found");
    }
    }