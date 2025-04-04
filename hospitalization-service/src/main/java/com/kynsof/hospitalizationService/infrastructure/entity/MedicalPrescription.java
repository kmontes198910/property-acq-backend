package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "medical_prescription")
public class MedicalPrescription {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospitalization_id", nullable = false)
    private Hospitalization hospitalization;

    private LocalDate prescriptionDate;
    private String instructions;

    public MedicalPrescription(MedicalPrescriptionDto dto) {
        this.id = dto.getId();
        this.hospitalization = dto.getHospitalization() != null ? new Hospitalization(dto.getHospitalization()) : null;
        this.prescriptionDate = dto.getPrescriptionDate();
        this.instructions = dto.getInstructions();
    }

    public MedicalPrescriptionDto toAggregate() {
        return new MedicalPrescriptionDto(id, hospitalization.toAggregate(), prescriptionDate, instructions);
    }

}
