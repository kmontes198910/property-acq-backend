package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedAssignment implements Serializable {
    private UUID id;
    private UUID patientId;
    private UUID surgeryId;
    private UUID bedId;
    private UUID roomId;
    private LocalDateTime assignmentDate;
    private LocalDateTime releaseDate;
    private String status;
    private String surgeryStage;
    private String observations;
    private UUID assignedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID businessId;
}