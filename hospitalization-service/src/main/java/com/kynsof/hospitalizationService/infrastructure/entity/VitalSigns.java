package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.VitalSignsDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "vital_signs")
public class VitalSigns {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "emergency_case_id", nullable = false)
    private EmergencyCase emergencyCase;

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

    public VitalSigns(VitalSignsDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase() != null ? new EmergencyCase(dto.getEmergencyCase()) : null;
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

    public VitalSignsDto toAggregate() {
        return new VitalSignsDto(id, emergencyCase.toAggregate(), systolicBloodPressure, diastolicBloodPressure, heartRate, respiratoryRate, temperature, weight, height, capillaryGlucose, glasgowScoreOcular, glasgowScoreVerbal, glasgowScoreMotor, recordedAt);
    }

}
