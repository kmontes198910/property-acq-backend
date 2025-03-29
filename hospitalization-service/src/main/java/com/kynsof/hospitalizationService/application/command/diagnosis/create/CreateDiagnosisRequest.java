package com.kynsof.hospitalizationService.application.command.diagnosis.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDiagnosisRequest {

    private UUID emergencyCase;
    private String diagnosisType;
    private String diagnosisDescription;
}
