package com.kynsoft.cirugia.application.command.diagnosis.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiagnosisRequest {
    private String icdCode;
    private String description;
    private String type;
    private UUID surgeryId;
}