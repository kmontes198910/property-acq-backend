package com.kynsoft.cirugia.domain.model;

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
public class BedAssignment {
    private UUID id;
    private UUID patientId;
    private UUID surgeryId;
    private UUID bedId;
    private LocalDateTime assignmentDate;
    private LocalDateTime plannedReleaseDate;
    private LocalDateTime actualReleaseDate;
    private String status;
    private String medicalNotes;
    private String vitalSigns;
    private String careInstructions;
    private UUID assignedBy;
    private UUID releasedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID businessId;
}