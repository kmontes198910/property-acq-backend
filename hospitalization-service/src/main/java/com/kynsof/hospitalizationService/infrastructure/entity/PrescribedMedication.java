package com.kynsof.hospitalizationService.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "prescribed_medication")
public class PrescribedMedication {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "medical_prescription_id", nullable = false)
    private MedicalPrescription medicalPrescription;

    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;
}
