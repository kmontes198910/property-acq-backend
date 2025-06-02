package com.kynsoft.propertyacqcenter.domain.dto.exception;

import java.util.UUID;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(UUID id) {
        super("Bank Account with ID " + id + " not found.");
    }
}
