package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post_operative")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostOperativeEntity {

    @Id
    private UUID id;

    @Column(name = "surgery_id", nullable = false, unique = true)
    private UUID surgeryId;

    @Column(name = "treatment_summary", columnDefinition = "TEXT")
    private String treatmentSummary;       // Resumen del Tratamiento

    @Column(name = "discharge_instructions", columnDefinition = "TEXT")
    private String dischargeInstructions;  // Instrucciones de Alta/Egreso

    @Column(name = "life_status")
    private String lifeStatus;             // Estado de Vida (VIVO, MUERTO)

    @Column(name = "discharge_condition")
    private String dischargeCondition;     // Condición de Alta/Egreso

    @Column(name = "stay_days")
    private Integer stayDays;              // Días de Estadía

    @Column(name = "rest_days")
    private Integer restDays;              // Días de Reposo

    @Column(name = "clinical_summary", columnDefinition = "TEXT")
    private String clinicalSummary;        // Resumen del Cuadro Clínico

    @Column(name = "evolution_summary", columnDefinition = "TEXT")
    private String evolutionSummary;       // Resumen de Evolución y Complicaciones

    @Column(name = "diagnostic_findings", columnDefinition = "TEXT")
    private String diagnosticFindings;     // Hallazgos Relevantes de Exámenes y Procedimientos Diagnósticos

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;
}