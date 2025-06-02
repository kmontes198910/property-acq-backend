package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vital_signs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalSignsEntity {
    
    @Id
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "patient_id")
    private UUID patientId;
    
    @Column(name = "surgery_id")
    private UUID surgeryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;
    
    @Column(name = "systolic_blood_pressure")
    private Integer systolicBloodPressure;
    
    @Column(name = "diastolic_blood_pressure")
    private Integer diastolicBloodPressure;
    
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "oxygen_saturation")
    private Double oxygenSaturation;

    @Column(name = "cranial_circumference")
    private Double cranialCircumference;
    
    @Column(name = "weight")
    private Double weight;
    
    @Column(name = "height")
    private Double height;
    
    @Column(name = "bmi")
    private Double bmi;
    
    @Column(name = "bmi_classification")
    private String bmiClassification;
    
    @Column(name = "capillary_glucose")
    private Double capillaryGlucose;
    
    @Column(name = "glasgow_score_motor")
    private Integer glasgowScoreMotor;
    
    @Column(name = "glasgow_score_verbal")
    private Integer glasgowScoreVerbal;
    
    @Column(name = "glasgow_score_ocular")
    private Integer glasgowScoreOcular;
    
    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "process")
    private String process;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
}