package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalSigns implements Serializable {
    private UUID id;
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
    private Double capillaryGlucose;
    private Integer glasgowScoreMotor;
    private Integer glasgowScoreVerbal;
    private Integer glasgowScoreOcular;
    private String observations;
    private LocalDateTime recordedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}