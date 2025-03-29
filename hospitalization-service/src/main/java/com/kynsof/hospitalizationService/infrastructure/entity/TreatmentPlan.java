package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.TreatmentPlanDto;
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
@Table(name = "treatment_plan")
public class TreatmentPlan {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "emergency_case_id", nullable = false)
    private EmergencyCase emergencyCase;

    private String medicationName;
    private String administrationRoute;
    private String dosage;
    private String frequency;
    private Integer daysOfTreatment;

    public TreatmentPlan(TreatmentPlanDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase() != null ? new EmergencyCase(dto.getEmergencyCase()) : null;
        this.medicationName = dto.getMedicationName();
        this.administrationRoute = dto.getAdministrationRoute();
        this.dosage = dto.getDosage();
        this.frequency = dto.getFrequency();
        this.daysOfTreatment = dto.getDaysOfTreatment();
    }

    public TreatmentPlanDto toAggregate() {
        return new TreatmentPlanDto(id, emergencyCase.toAggregate(), medicationName, administrationRoute, dosage, frequency, daysOfTreatment);
    }

}
