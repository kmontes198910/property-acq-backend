package com.kynsoft.cirugia.application.command.preOperative.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePreOperativeRequest {
    private UUID surgeryId;
    private String admissionReason;
    private String currentDiseaseHistory;
    private String physicalExamination;
}