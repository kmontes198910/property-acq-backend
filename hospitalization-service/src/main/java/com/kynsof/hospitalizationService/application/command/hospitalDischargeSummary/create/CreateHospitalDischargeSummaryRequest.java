package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateHospitalDischargeSummaryRequest {

    private UUID hospitalization;
    private String dischargeDate;
    private String finalDiagnosis;
    private String treatmentsPerformed;
    private String recommendations;
}
