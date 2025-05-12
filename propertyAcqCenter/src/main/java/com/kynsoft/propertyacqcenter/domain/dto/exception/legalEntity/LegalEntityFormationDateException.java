package com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity;

import java.time.LocalDate;

public class LegalEntityFormationDateException extends RuntimeException {
    public LegalEntityFormationDateException(LocalDate formationDate) {
        super("The formation date cannot be in the future.");
    }
}
