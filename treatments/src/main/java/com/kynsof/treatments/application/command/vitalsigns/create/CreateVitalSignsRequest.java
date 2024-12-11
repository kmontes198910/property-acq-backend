package com.kynsof.treatments.application.command.vitalsigns.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateVitalSignsRequest {
    private UUID patientId;
    private String bloodPressure;
    private Integer heartRate;
    private Integer oxygenSaturation;
    private Integer respiratoryRate;
    private Double temperature;
    private Double weight; // Peso en kilogramos
    private Double height; // Altura en metros o centímetros
    private Double cranialCircumference; // Circunferencia craneal en centímetros
}