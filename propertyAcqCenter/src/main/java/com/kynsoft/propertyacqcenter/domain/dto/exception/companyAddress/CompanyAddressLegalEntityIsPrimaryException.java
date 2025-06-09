package com.kynsoft.propertyacqcenter.domain.dto.exception.companyAddress;

public class CompanyAddressLegalEntityIsPrimaryException extends RuntimeException {
    public CompanyAddressLegalEntityIsPrimaryException() {
        super("A primary address is already designated for this company.");
    }
}
