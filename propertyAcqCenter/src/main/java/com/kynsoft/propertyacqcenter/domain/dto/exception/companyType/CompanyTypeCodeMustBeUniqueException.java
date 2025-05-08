package com.kynsoft.propertyacqcenter.domain.dto.exception.companyType;

public class CompanyTypeCodeMustBeUniqueException extends RuntimeException {
    public CompanyTypeCodeMustBeUniqueException(String code) {
        super("The code: " + code + " already exists. ");
    }
}
