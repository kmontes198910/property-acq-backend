package com.kynsoft.propertyacqcenter.domain.dto.exception.business;

public class BusinessNameMustBeUniqueException extends RuntimeException {
    public BusinessNameMustBeUniqueException(String name) {
        super("The name: " + name + " already exists. ");
    }
}
