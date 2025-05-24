package com.kynsoft.invoiceservice.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotación para validar que el campo rimpeRegime solo contenga valores permitidos
 */
@Documented
@Constraint(validatedBy = RimpeRegimeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRimpeRegime {
    String message() default "El régimen RIMPE solo puede ser 'CONTRIBUYENTE RÉGIMEN RIMPE', 'CONTRIBUYENTE NEGOCIO POPULAR - RÉGIMEN RIMPE' o nulo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
