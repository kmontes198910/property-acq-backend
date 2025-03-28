package com.kynsof.hospitalizationService.application.command.vitalSigns.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateVitalSignsRequest {

    private UUID emergencyCase;
    private Double systolicBloodPressure;
    private Double diastolicBloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private Double temperature;
    private Double weight;
    private Double height;
    private Double capillaryGlucose;
    private Integer glasgowScoreOcular;
    private Integer glasgowScoreVerbal;
    private Integer glasgowScoreMotor;
}
