package com.kynsoft.cirugia.application.command.update;

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
public class UpdateSurgeryRequest {
    private UUID surgeryId;
    private UUID patientId;
    private UUID doctorId;
    private UUID specialtyId;
    private String surgeryType;
    private String description;
    private LocalDateTime scheduledDate;
    private Integer estimatedDurationMinutes;
    private String complexityLevel;
    private UUID roomId;
    private Boolean requiresHospitalization;
    private String admissionReason;
    private String currentIllnessHistory;
    private String physicalExamination;
    private UUID updatedBy;
}