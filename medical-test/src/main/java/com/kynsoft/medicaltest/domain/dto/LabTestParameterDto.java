package com.kynsoft.medicaltest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Objeto de transferencia de datos (DTO) para la entidad LabTestParameter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestParameterDto {
    
    private UUID id;
    private String code;
    private UUID labTestId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
