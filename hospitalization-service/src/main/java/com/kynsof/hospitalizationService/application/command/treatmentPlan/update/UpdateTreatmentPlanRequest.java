package com.kynsof.hospitalizationService.application.command.treatmentPlan.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTreatmentPlanRequest {

    private UUID emergencyCase;
    private String medicationName;
    private String administrationRoute;
    private String dosage;
    private String frequency;
    private Integer daysOfTreatment;
}
