package com.kynsoft.medicaltest.domain.exception;

/**
 * Excepción para entidades no encontradas
 */
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String entityType, String identifier) {
        super(String.format("%s con identificador %s no encontrado", entityType, identifier));
    }
}
