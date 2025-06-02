package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOperative {

    private UUID id;
    private UUID surgeryId;
    private String treatmentSummary;       // Resumen del Tratamiento
    private String dischargeInstructions;  // Instrucciones de Alta/Egreso
    private String lifeStatus;             // Estado de Vida (VIVO, MUERTO)
    private String dischargeCondition;     // Condición de Alta/Egreso
    private Integer stayDays;              // Días de Estadía
    private Integer restDays;              // Días de Reposo
    private String clinicalSummary;        // Resumen del Cuadro Clínico
    private String evolutionSummary;       // Resumen de Evolución y Complicaciones
    private String diagnosticFindings;     // Hallazgos Relevantes de Exámenes y Procedimientos Diagnósticos
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}