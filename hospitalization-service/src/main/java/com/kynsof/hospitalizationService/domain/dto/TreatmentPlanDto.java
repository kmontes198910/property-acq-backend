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
public class TreatmentPlanDto {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String medicationName;
    private String administrationRoute;
    private String dosage;
    private String frequency;
    private Integer daysOfTreatment;
}
