package com.kynsoft.cirugia.application.command.surgery.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private UUID recoveryBedEntityId;
    private UUID operatingRoomId;
    private String surgeryType;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endingTime;
    private Boolean requiresHospitalization;
    private UUID updatedBy;
}