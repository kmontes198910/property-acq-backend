package com.kynsoft.medicaltest.application.command.labtestparameter.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Clase que representa la solicitud para actualizar un parámetro de examen de laboratorio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestParameterRequest {
    
    private String name;
    private String unit;
    private BigDecimal referenceRangeMin;
    private BigDecimal referenceRangeMax;
    private Boolean genderSpecific;
}
