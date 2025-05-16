package com.kynsoft.cirugia.application.command.treatment.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTreatmentRequest {
    private UUID surgeryId; // Ahora opcional
    private UUID patientId; // Nuevo campo obligatorio
    private String code;
    private String name;
    private String description;
    private int quantity;
    private String medicineUnit;
    private String status;
    private String process;
}