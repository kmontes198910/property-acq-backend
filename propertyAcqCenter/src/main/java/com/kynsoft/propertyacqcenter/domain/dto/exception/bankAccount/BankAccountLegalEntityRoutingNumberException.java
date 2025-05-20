package com.kynsoft.propertyacqcenter.domain.dto.exception.bankAccount;

public class BankAccountLegalEntityRoutingNumberException extends RuntimeException {
    public BankAccountLegalEntityRoutingNumberException() {
        super("Invalid input: must contain exactly 9 digits.");
    }
}
