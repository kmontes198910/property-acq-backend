package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for representing patient admission data for surgery
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmisionDto {
    private UUID id;
    private UUID room;
    private UUID bed;
    private String observations;
    private UUID surgeryId;
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
