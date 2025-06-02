package com.kynsoft.cirugia.application.command.vitalsigns.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVitalSignsRequest {
    private UUID patientId;
    private UUID surgeryId;
    private Integer systolicBloodPressure;
    private Integer diastolicBloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private Double temperature;
    private Double oxygenSaturation;
    private Double weight;
    private Double height;
    private Double cranialCircumference;
    private Double capillaryGlucose;
    private Integer glasgowScoreMotor;
    private Integer glasgowScoreVerbal;
    private Integer glasgowScoreOcular;
    private String observations;
    private String process;
}