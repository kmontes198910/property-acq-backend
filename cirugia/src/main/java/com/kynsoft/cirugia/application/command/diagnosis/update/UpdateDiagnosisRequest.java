package com.kynsoft.cirugia.application.command.diagnosis.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDiagnosisRequest {
    private String icdCode;
    private String description;
    private String type;
    private UUID surgeryId;
    private UUID updatedBy;
}