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
    private String code;
    private UUID labTestId;
    private String name;
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
        this.code = dto.getCode();
        this.labTestId = dto.getLabTestId();
        this.name = dto.getName();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }


}
