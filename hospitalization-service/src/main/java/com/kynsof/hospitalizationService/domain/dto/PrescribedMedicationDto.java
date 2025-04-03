package com.kynsof.hospitalizationService.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescribedMedicationDto {
    private UUID id;
    private MedicalPrescriptionDto medicalPrescription;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;
}
