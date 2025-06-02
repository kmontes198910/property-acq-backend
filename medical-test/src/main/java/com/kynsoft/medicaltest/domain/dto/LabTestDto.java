package com.kynsoft.medicaltest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Objeto de transferencia de datos (DTO) para la entidad LabTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestDto {
    
    private UUID id;
    private String code;
    private String name;
    private String category;
    private String description;
    @Builder.Default
    private List<LabTestParameterDto> parameters = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
