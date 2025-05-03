package com.kynsoft.cirugia.application.command.postOperative.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostOperativeRequest {
    private UUID surgeryId;
    private String treatmentSummary;
    private String dischargeInstructions;
    private String lifeStatus;
    private String dischargeCondition;
    private Integer stayDays;
    private Integer restDays;
    private String clinicalSummary;
    private String evolutionSummary;
    private String diagnosticFindings;
}