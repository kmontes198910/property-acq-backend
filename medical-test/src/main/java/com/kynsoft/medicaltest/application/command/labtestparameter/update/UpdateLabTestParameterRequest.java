package com.kynsoft.medicaltest.application.command.labtestparameter.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Clase que representa la solicitud para actualizar un parámetro de examen de laboratorio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestParameterRequest {
    private String code;
    private String name;
    private UUID labTestId;
}
