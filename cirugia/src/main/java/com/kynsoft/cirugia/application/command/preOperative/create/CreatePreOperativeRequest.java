package com.kynsoft.cirugia.application.command.preOperative.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePreOperativeRequest {
    private UUID surgeryId;
    private String admissionReason;
    private String currentDiseaseHistory;
    private String physicalExamination;
    private String consentInformedFileUrl;
}