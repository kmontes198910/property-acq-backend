package com.kynsoft.medicaltest.application.query.labtest.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Respuesta para la consulta por ID de un examen de laboratorio
 */
@Getter
public class LabTestResponse implements IResponse, Serializable {
    private final UUID id;
    private final String code;
    private final String name;
    private final String description;
   // private final List<LabTestParameterResponse> parameters;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;
    
    /**
     * Constructor que convierte un DTO a una respuesta
     * 
     * @param labTestDto El DTO a convertir
     */
    public LabTestResponse(LabTestDto labTestDto) {
        this.id = labTestDto.getId();
        this.code = labTestDto.getCode();
        this.name = labTestDto.getName();
        this.description = labTestDto.getDescription();
//        this.parameters = labTestDto.getParameters() != null
//            ? labTestDto.getParameters().stream()
//                .map(LabTestParameterResponse::new)
//                .collect(Collectors.toList())
//            : new ArrayList<>();
        this.createdAt = labTestDto.getCreatedAt();
        this.updatedAt = labTestDto.getUpdatedAt();
        this.createdBy = labTestDto.getCreatedBy();
        this.updatedBy = labTestDto.getUpdatedBy();
    }
    
    /**
     * Clase interna para representar un parámetro en la respuesta
     */
    @Getter
    public static class LabTestParameterResponse implements Serializable {
        private final UUID id;
        private final String name;
        
        public LabTestParameterResponse(LabTestParameterDto dto) {
            this.id = dto.getId();
            this.name = dto.getName();

        }
        

    }
}
