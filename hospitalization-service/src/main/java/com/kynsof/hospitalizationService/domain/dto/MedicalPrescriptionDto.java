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
public class MedicalPrescriptionDto {
    private UUID id;
    private HospitalizationDto hospitalization;
    private LocalDate prescriptionDate;
    private String instructions;
}
