package com.kynsoft.medicaltest.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO para transferir información de órdenes de examen entre capas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationOrderDto {
    
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private LocalDateTime creationDate;
    private String status;
    private String observations;
    private UUID businessId;
    
    @Builder.Default
    private List<ExaminationDto> examinations = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
