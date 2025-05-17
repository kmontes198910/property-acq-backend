package com.kynsoft.medicaltest.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para transferir información de exámenes entre capas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationDto {
    
    private UUID id;
    private UUID orderId;
    private String code;
    private String examinationType;
    private String status;
    private String results;
    private LocalDateTime completionDate;
    private String observations;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
