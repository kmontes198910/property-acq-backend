package com.kynsof.hospitalizationService.application.command.medicalPrescription.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMedicalPrescriptionRequest {

    private UUID hospitalization;
    private String prescriptionDate;
    private String instructions;
}
