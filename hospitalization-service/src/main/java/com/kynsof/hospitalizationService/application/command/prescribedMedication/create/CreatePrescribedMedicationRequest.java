package com.kynsof.hospitalizationService.application.command.prescribedMedication.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePrescribedMedicationRequest {

    private UUID medicalPrescription;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;
}
