package com.kynsoft.propertyacqcenter.domain.dto.exception.address;

public class AddressLegalEntityIsPrimaryException extends RuntimeException {
    public AddressLegalEntityIsPrimaryException() {
        super("A primary address is already designated for this legal entity.");
    }
}
