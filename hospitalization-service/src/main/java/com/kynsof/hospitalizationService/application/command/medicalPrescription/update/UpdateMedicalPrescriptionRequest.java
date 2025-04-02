package com.kynsof.hospitalizationService.application.command.medicalPrescription.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMedicalPrescriptionRequest {

    private UUID hospitalization;
    private String prescriptionDate;
    private String instructions;
}
