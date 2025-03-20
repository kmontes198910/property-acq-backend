package com.kynsof.treatments.application.query.vitalsigns.search;


import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.VitalSignsDto;
import com.kynsof.treatments.domain.dto.enumDto.BMIClassification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class VitalSignsResponse implements IResponse {
    private UUID id;
    private String bloodPressure; // Ejemplo: "120/80"
    private Integer heartRate; // Frecuencia cardíaca (lpm)
    private Integer oxygenSaturation; // Saturación de oxígeno (%)
    private Integer respiratoryRate; // Frecuencia respiratoria (rpm)
    private Double temperature; // Temperatura corporal (°C)
    private Double weight; // Peso en kilogramos
    private Double height; // Altura en metros o centímetros
    private Double cranialCircumference; // Circunferencia craneal en centímetros
    private PatientDto patient;
    private LocalDate vitalSignDate;
    private double calculateBMI;
    private BMIClassification bmiClassification;


    public VitalSignsResponse(VitalSignsDto dto) {
        this.id = dto.getId();
       this.bloodPressure = dto.getBloodPressure();
       this.heartRate = dto.getHeartRate();
       this.oxygenSaturation = dto.getOxygenSaturation();
       this.respiratoryRate = dto.getRespiratoryRate();
       this.temperature = dto.getTemperature();
       this.weight = dto.getWeight();
       this.height = dto.getHeight();
       this.cranialCircumference = dto.getCranialCircumference();
       this.patient = dto.getPatient();
       this.vitalSignDate = dto.getVitalSignDate().toLocalDate();
       this.calculateBMI = dto.getCalculateBMI();
       this.bmiClassification = dto.getBmiClassification();
    }

}