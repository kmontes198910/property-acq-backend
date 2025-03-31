package com.kynsof.hospitalizationService.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiagnosisDto {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String diagnosisType;
    private String diagnosisDescription;
    private String code;
}
