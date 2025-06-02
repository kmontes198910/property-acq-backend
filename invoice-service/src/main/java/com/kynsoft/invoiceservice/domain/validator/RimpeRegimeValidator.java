package com.kynsoft.invoiceservice.domain.validator;

import ec.e.facturacion.sri.constante.Regimen;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para asegurar que el campo rimpeRegime solo contenga valores permitidos
 */
public class RimpeRegimeValidator implements ConstraintValidator<ValidRimpeRegime, String> {

    @Override
    public void initialize(ValidRimpeRegime constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Permite valores nulos
        }
        return value.equals(Regimen.REGIMEN_RIMPE) || 
               value.equals(Regimen.NEGOCIO_POPULAR);
    }
}
