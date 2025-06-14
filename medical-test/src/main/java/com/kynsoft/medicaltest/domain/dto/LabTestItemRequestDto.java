package com.kynsoft.medicaltest.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestItemRequestDto {
    
    private UUID id;
    private LabTestRequestDto order;
    private String code;
    private String description;
    private String status;
    private LocalDate completionDate;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private List<LabTestResultDto> testResults;
    
}
