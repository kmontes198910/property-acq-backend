package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
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
public class VitalSignsResponse implements IResponse {
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

    public VitalSignsResponse(VitalSignsDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase();
        this.systolicBloodPressure = dto.getSystolicBloodPressure();
        this.diastolicBloodPressure = dto.getDiastolicBloodPressure();
        this.heartRate = dto.getHeartRate();
        this.respiratoryRate = dto.getRespiratoryRate();
        this.temperature = dto.getTemperature();
        this.weight = dto.getWeight();
        this.height = dto.getHeight();
        this.capillaryGlucose = dto.getCapillaryGlucose();
        this.glasgowScoreOcular = dto.getGlasgowScoreOcular();
        this.glasgowScoreVerbal = dto.getGlasgowScoreVerbal();
        this.glasgowScoreMotor = dto.getGlasgowScoreMotor();
        this.recordedAt = dto.getRecordedAt();
    }

    
}
