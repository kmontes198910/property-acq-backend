package com.kynsoft.medicaltest.application.command.labtest.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Objeto de solicitud para actualizar un examen de laboratorio existente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestRequest {
    private String code;
    private String name;
    private String category;
    private String description;
    private List<LabTestParameterRequest> parameters;
    
    /**
     * Clase interna para representar un parámetro en la solicitud de actualización
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabTestParameterRequest {
        private UUID id;
        private String name;
        private String unit;
        @JsonProperty("reference_range_min")
        private BigDecimal referenceRangeMin;
        @JsonProperty("reference_range_max")
        private BigDecimal referenceRangeMax;
        @JsonProperty("gender_specific")
        private Boolean genderSpecific;
    }
}
