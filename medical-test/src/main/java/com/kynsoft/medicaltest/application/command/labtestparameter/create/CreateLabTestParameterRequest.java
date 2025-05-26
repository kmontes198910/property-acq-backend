package com.kynsoft.medicaltest.application.command.labtestparameter.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Clase que representa la solicitud para crear un nuevo parámetro de examen de laboratorio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestParameterRequest {
    
    private UUID labTestId;
    private String code;
    private String name;
    private String unit;
    private BigDecimal referenceRangeMin;
    private BigDecimal referenceRangeMax;
    private Boolean genderSpecific;
}
