package com.kynsof.treatments.application.command.externalConsultation.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDiagnosisAllRequest {
    private UUID id; // Nuevo campo
    private String icdCode;
    private String description;
}