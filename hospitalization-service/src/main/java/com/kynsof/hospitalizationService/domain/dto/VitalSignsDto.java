package com.kynsof.hospitalizationService.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VitalSignsDto {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
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
    private LocalDateTime recordedAt;
}
