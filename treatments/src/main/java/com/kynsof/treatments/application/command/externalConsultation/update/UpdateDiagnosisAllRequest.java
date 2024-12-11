package com.kynsof.treatments.application.command.externalConsultation.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDiagnosisAllRequest {
    private String icdCode; // Código CIE-10
    private String description;
}
