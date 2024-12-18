package com.kynsof.treatments.application.command.diagnosis.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDiagnosisRequest {
    private String icdCode; // Código CIE-10
    private String description;
    private  UUID idExternalConsultation;
}
