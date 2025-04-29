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
public class Surgery {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private UUID specialtyId;
    private String surgeryType;
    private String description;
    private LocalDateTime scheduledDate;
    private LocalDateTime performedDate;
    private Integer estimatedDurationMinutes;
    private String status;  // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    private String preoperativeNotes;
    private String postoperativeNotes;
    private String complexityLevel;  // LOW, MEDIUM, HIGH
    private UUID roomId;
    private Boolean requiresHospitalization;
    private String admissionReason;
    private String currentIllnessHistory;
    private String physicalExamination;
    private LocalDateTime operatingRoomEntryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID businessId;
}