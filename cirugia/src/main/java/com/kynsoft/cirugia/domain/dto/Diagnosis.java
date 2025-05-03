package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis {
    private UUID id;
    private String icdCode; // Código CIE-10
    private String description;
    private String type; // Tipo de diagnóstico (presuntivo, definitivo, etc.)
    private UUID surgeryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}