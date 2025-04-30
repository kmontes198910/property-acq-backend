package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private UUID recoveryBedEntityId;
    private UUID operatingRoomId;
    private String surgeryType;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endingTime;
    private Boolean requiresHospitalization;
    private String status;  // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID businessId;
}