package com.kynsof.hospitalizationService.application.command.prescribedMedication.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePrescribedMedicationRequest {

    private UUID medicalPrescription;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;
}
