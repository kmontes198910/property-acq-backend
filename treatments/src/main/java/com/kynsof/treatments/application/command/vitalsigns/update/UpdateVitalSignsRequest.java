package com.kynsof.treatments.application.command.vitalsigns.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateVitalSignsRequest {
    private String bloodPressure; // Ejemplo: "120/80"
    private Integer heartRate; // Frecuencia cardíaca (lpm)
    private Integer oxygenSaturation; // Saturación de oxígeno (%)
    private Integer respiratoryRate; // Frecuencia respiratoria (rpm)
    private Double temperature; // Temperatura corporal (°C)
    private UUID patientId; // ID del paciente
    private Double weight; // Peso en kilogramos
    private Double height; // Altura en metros o centímetros
    private Double cranialCircumference; // Circunferencia craneal en centímetros
}