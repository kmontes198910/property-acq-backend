package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.TreatmentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Treatment {

    @Id
    private UUID id;

    private String description;
    private int quantity;
    private String medicineUnit;

    @Column(name = "prescription_number", unique = true)
    private Long prescriptionNumber;

    @ManyToOne
    @JoinColumn(name = "external_consultation_id")
    private ExternalConsultation externalConsultation;

    @ManyToOne
    @JoinColumn(name = "medicines_id")
    private Medicines medicines;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Treatment(TreatmentDto treatmentDto) {
        this.id = treatmentDto.getId();
        this.description = treatmentDto.getDescription();
        this.medicines = treatmentDto.getMedication() != null ? new Medicines(treatmentDto.getMedication()) : null;
        this.quantity = treatmentDto.getQuantity();
        this.medicineUnit = treatmentDto.getMedicineUnit();
        this.externalConsultation = treatmentDto.getExternalConsultationDto() != null ? new ExternalConsultation(treatmentDto.getExternalConsultationDto()) : null;
    }

    public TreatmentDto toAggregate() {
        return new TreatmentDto(this.id, this.description, this.medicines.toAggregate(), this.quantity, this.medicineUnit);
    }
}
