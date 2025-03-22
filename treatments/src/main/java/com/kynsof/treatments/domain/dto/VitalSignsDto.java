package com.kynsof.treatments.domain.dto;

import com.kynsof.treatments.domain.dto.enumDto.BMIClassification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VitalSignsDto {

    private  UUID id;
    private  String bloodPressure; // Ejemplo: "120/80"
    private  Integer heartRate; // Frecuencia cardíaca (lpm)
    private  Integer oxygenSaturation; // Saturación de oxígeno (%)
    private  Integer respiratoryRate; // Frecuencia respiratoria (rpm)
    private  Double temperature; // Temperatura corporal (°C)
    private  PatientDto patient; // Relación con el paciente
    private  Double weight; // Peso en kilogramos
    private  Double height; // Altura en metros o centímetros
    private  Double cranialCircumference; // Circunferencia craneal en centímetros
    private LocalDateTime vitalSignDate;
    private double calculateBMI;
    private BMIClassification bmiClassification;
    public VitalSignsDto(UUID id, String bloodPressure, Integer heartRate, Integer oxygenSaturation,
                         Integer respiratoryRate, Double temperature, PatientDto patient, Double weight, Double height, Double cranialCircumference) {
        this.id = id;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
        this.temperature = temperature;
        this.patient = patient;
        this.weight = weight;
        this.height = height;
        this.cranialCircumference = cranialCircumference;
    }
}