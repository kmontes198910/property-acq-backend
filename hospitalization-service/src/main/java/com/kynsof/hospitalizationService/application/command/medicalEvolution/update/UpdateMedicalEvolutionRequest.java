package com.kynsof.hospitalizationService.application.command.medicalEvolution.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMedicalEvolutionRequest {

    private UUID hospitalization;
    private String recordDate;
    private String observations;
    private String diagnosis;
}
