package com.kynsof.hospitalizationService.application.command.medicalEvolution.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMedicalEvolutionRequest {

    private UUID hospitalization;
    private String recordDate;
    private String observations;
    private String diagnosis;
}
