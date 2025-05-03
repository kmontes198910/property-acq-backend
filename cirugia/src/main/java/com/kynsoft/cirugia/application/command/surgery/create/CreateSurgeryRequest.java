package com.kynsoft.cirugia.application.command.surgery.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSurgeryRequest {
    private UUID patientId;
    private UUID doctorId;
    private UUID specialtyId;
    private UUID recoveryBedEntityId;
    private UUID businessId;
    private String surgeryType;
    private Boolean requiresHospitalization;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endingTime;
}