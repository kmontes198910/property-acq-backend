package com.kynsof.hospitalizationService.domain.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HospitalizationDto {
    private UUID id;
    private PatientDto patient;
    private EmergencyCaseDto emergencyCase;
    private MedicalStaffDto attendingDoctor;
    private LocalDate admissionDate;
    private String assignedRoom;
    private String hospitalizationStatus;
}
