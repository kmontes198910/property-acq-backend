package com.kynsof.hospitalizationService.application.command.diagnosis.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDiagnosisRequest {

    private UUID emergencyCase;
    private String diagnosisType;
    private String diagnosisDescription;
    private String code;
}
