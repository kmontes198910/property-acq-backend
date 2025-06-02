package com.kynsoft.medicaltest.application.command.labtest.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Objeto de solicitud para crear un nuevo examen de laboratorio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestRequest {
    private String code;
    private String name;
    private String category;
    private String description;
}
