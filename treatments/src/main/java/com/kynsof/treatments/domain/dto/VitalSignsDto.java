package com.kynsof.treatments.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VitalSignsDto {

    private UUID id;
    private String bloodPressure; // Ejemplo: "120/80"
    private Integer heartRate; // Frecuencia cardíaca (lpm)
    private Integer oxygenSaturation; // Saturación de oxígeno (%)
    private Integer respiratoryRate; // Frecuencia respiratoria (rpm)
    private Double temperature; // Temperatura corporal (°C)
    private PatientDto patient; // Relación con el paciente
    private Double weight; // Peso en kilogramos
    private Double height; // Altura en metros o centímetros
    private Double cranialCircumference; // Circunferencia craneal en centímetros
}