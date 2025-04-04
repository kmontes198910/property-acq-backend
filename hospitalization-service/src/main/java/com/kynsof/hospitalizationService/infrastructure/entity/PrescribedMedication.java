package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.PrescribedMedicationDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_prescription_id", nullable = false)
    private MedicalPrescription medicalPrescription;

    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;

    public PrescribedMedication(PrescribedMedicationDto dto) {
        this.id = dto.getId();
        this.medicalPrescription = dto.getMedicalPrescription() != null ? new MedicalPrescription(dto.getMedicalPrescription()) : null;
        this.medicationName = dto.getMedicationName();
        this.dosage = dto.getDosage();
        this.frequency = dto.getFrequency();
        this.administrationRoute = dto.getAdministrationRoute();
        this.duration = dto.getDuration();
    }

    public PrescribedMedicationDto toAggregate() {
        return new PrescribedMedicationDto(id, medicalPrescription.toAggregate(), medicationName, dosage, frequency, administrationRoute, duration);
    }

}
