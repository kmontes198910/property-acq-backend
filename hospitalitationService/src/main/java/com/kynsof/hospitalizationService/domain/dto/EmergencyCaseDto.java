package com.kynsof.hospitalizationService.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmergencyCaseDto {
    private UUID id;
    private PatientDto patient;
    private LocalDate admissionDate;
    private LocalTime admissionTime;
    private String admissionType;
    private String status;
}
