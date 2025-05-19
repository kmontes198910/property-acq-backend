package com.kynsoft.cirugia.application.query.vitalsigns.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class VitalSignsResponse implements IResponse, Serializable {
    private final UUID id;
    private final UUID patientId;
    private final UUID surgeryId;
    private final Integer systolicBloodPressure;
    private final Integer diastolicBloodPressure;
    private final Integer heartRate;
    private final Integer respiratoryRate;
    private final Double temperature;
    private final Double oxygenSaturation;
    private final Double weight;
    private final Double height;
    private final Double bmi;
    private final String bmiClassification;
    private final Double capillaryGlucose;
    private final Integer glasgowScoreMotor;
    private final Integer glasgowScoreVerbal;
    private final Integer glasgowScoreOcular;
    private final String observations;
    private final LocalDateTime recordedAt;
    private String process;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;
    
    public VitalSignsResponse(VitalSigns vitalSigns) {
        this.id = vitalSigns.getId();
        this.patientId = vitalSigns.getPatientId();
        this.surgeryId = vitalSigns.getSurgeryId();
        this.systolicBloodPressure = vitalSigns.getSystolicBloodPressure();
        this.diastolicBloodPressure = vitalSigns.getDiastolicBloodPressure();
        this.heartRate = vitalSigns.getHeartRate();
        this.respiratoryRate = vitalSigns.getRespiratoryRate();
        this.temperature = vitalSigns.getTemperature();
        this.oxygenSaturation = vitalSigns.getOxygenSaturation();
        this.weight = vitalSigns.getWeight();
        this.height = vitalSigns.getHeight();
        this.bmi = vitalSigns.getBmi();
        this.bmiClassification = vitalSigns.getBmiClassification();
        this.capillaryGlucose = vitalSigns.getCapillaryGlucose();
        this.glasgowScoreMotor = vitalSigns.getGlasgowScoreMotor();
        this.glasgowScoreVerbal = vitalSigns.getGlasgowScoreVerbal();
        this.glasgowScoreOcular = vitalSigns.getGlasgowScoreOcular();
        this.observations = vitalSigns.getObservations();
        this.recordedAt = vitalSigns.getRecordedAt();
        this.createdAt = vitalSigns.getCreatedAt();
        this.updatedAt = vitalSigns.getUpdatedAt();
        this.createdBy = vitalSigns.getCreatedBy();
        this.updatedBy = vitalSigns.getUpdatedBy();
        this.process = vitalSigns.getProcess();
    }
}