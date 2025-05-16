package com.kynsoft.propertyacqcenter.domain.dto.exception.bankAccount;

public class BankAccountLegalEntityAccountNumberException extends RuntimeException {
    public BankAccountLegalEntityAccountNumberException(String accountNumber) {
        super("Account number: " + accountNumber +", already exists for the specified legal entity.");
    }
}
