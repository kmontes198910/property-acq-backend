package com.kynsoft.propertyacqcenter.domain.dto.exception.subCompanyType;

public class CodeMustBeUniqueException extends RuntimeException {
    public CodeMustBeUniqueException(String code) {
        super("The code: " + code + " already exists. ");
    }
}
