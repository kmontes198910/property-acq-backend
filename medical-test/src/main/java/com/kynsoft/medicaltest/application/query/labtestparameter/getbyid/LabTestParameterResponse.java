package com.kynsoft.medicaltest.application.query.labtestparameter.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Respuesta para la consulta de un parámetro de examen de laboratorio por ID
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestParameterResponse implements IResponse {
    
    private UUID id;
    private UUID labTestId;
    private String name;
    private String unit;
    private BigDecimal referenceRangeMin;
    private BigDecimal referenceRangeMax;
    private String referenceRange;
    private Boolean genderSpecific;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    /**
     * Constructor que crea una respuesta a partir de un DTO
     *
     * @param dto El DTO con los datos del parámetro
     */
    public LabTestParameterResponse(LabTestParameterDto dto) {
        this.id = dto.getId();
        this.labTestId = dto.getLabTestId();
        this.name = dto.getName();
        this.unit = dto.getUnit();
        this.referenceRangeMin = dto.getReferenceRangeMin();
        this.referenceRangeMax = dto.getReferenceRangeMax();
        this.referenceRange = formatReferenceRange(dto);
        this.genderSpecific = dto.getGenderSpecific();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    /**
     * Formatea el rango de referencia como una cadena de texto
     *
     * @param dto El DTO con los datos del parámetro
     * @return Cadena formateada del rango de referencia
     */
    private String formatReferenceRange(LabTestParameterDto dto) {
        if (dto.getReferenceRangeMin() != null && dto.getReferenceRangeMax() != null) {
            return dto.getReferenceRangeMin() + " - " + dto.getReferenceRangeMax() + " " + dto.getUnit();
        } else if (dto.getReferenceRangeMin() != null) {
            return "> " + dto.getReferenceRangeMin() + " " + dto.getUnit();
        } else if (dto.getReferenceRangeMax() != null) {
            return "< " + dto.getReferenceRangeMax() + " " + dto.getUnit();
        }
        return "No especificado";
    }
}
