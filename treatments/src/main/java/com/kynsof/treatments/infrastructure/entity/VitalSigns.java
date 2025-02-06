package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.VitalSignsDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vital_signs")
public class VitalSigns {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String bloodPressure; // Ejemplo: "120/80"
    private Integer heartRate; // Frecuencia cardíaca (lpm)
    private Integer oxygenSaturation; // Saturación de oxígeno (%)
    private Integer respiratoryRate; // Frecuencia respiratoria (rpm)
    private Double temperature; // Temperatura corporal (°C)
    private Double weight; // Peso en kilogramos
    private Double height; // Altura en metros o centímetros
    private Double cranialCircumference; // Circunferencia craneal en centímetros

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructor que llena los valores desde el DTO
    public VitalSigns(VitalSignsDto dto) {
        this.id = dto.getId();
        this.bloodPressure = dto.getBloodPressure();
        this.heartRate = dto.getHeartRate();
        this.oxygenSaturation = dto.getOxygenSaturation();
        this.respiratoryRate = dto.getRespiratoryRate();
        this.temperature = dto.getTemperature();
        this.weight = dto.getWeight();
        this.height = dto.getHeight();
        this.cranialCircumference = dto.getCranialCircumference();
        this.patient = dto.getPatient() != null ? new Patients(dto.getPatient()) : null;
    }

    // Método toAggregate para convertir a DTO
    public VitalSignsDto toAggregate() {
        VitalSignsDto dto = new VitalSignsDto();
        dto.setId(this.id);
        dto.setBloodPressure(this.bloodPressure);
        dto.setHeartRate(this.heartRate);
        dto.setOxygenSaturation(this.oxygenSaturation);
        dto.setRespiratoryRate(this.respiratoryRate);
        dto.setTemperature(this.temperature);
        dto.setPatient(this.patient != null ? this.patient.toAggregate() : null);
        dto.setWeight(this.weight);
        dto.setHeight(this.height);
        dto.setCranialCircumference(this.cranialCircumference);
        dto.setVitalSignDate(this.createdAt);
        return dto;
    }
}