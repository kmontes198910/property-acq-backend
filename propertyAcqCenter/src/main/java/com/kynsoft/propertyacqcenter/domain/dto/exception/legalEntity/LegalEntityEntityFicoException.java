package com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity;

public class LegalEntityEntityFicoException extends RuntimeException {
    public LegalEntityEntityFicoException(Float fico) {
        super("El puntaje FICO debe estar entre 300 y 850.");
    }
}
