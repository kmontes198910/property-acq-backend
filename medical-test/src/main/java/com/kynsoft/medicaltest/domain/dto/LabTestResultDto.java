package com.kynsoft.medicaltest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestResultDto {
    
    private UUID id;
    
    private LabTestItemRequestDto labTestItem;
    private LabTestParameterDto labTestParameter;
    private String value;
    private String unit;
    private String flag;//(normal, low, high, critical)
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
