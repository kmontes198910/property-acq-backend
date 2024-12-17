package com.kynsof.treatments.application.command.diagnosis.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DiagnosisRequest {
    private String icdCode; // Código CIE-10
    private String description;
    private  UUID idExternalConsultation;
}
