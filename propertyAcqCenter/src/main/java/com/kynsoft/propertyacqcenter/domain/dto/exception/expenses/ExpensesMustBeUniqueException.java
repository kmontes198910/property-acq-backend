package com.kynsoft.propertyacqcenter.domain.dto.exception.expenses;

public class ExpensesMustBeUniqueException extends RuntimeException {
    public ExpensesMustBeUniqueException(String email) {
        super("A expenses already exists for this property.");
    }
}
