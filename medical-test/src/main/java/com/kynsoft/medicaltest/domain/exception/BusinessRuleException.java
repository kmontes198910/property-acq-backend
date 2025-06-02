package com.kynsoft.medicaltest.domain.exception;

/**
 * Excepción para operaciones de negocio inválidas
 */
public class BusinessRuleException extends RuntimeException {
    
    public BusinessRuleException(String message) {
        super(message);
    }
}
