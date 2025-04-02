package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.MedicalEvolutionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "medical_evolution")
public class MedicalEvolution {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "hospitalization_id", nullable = false)
    private Hospitalization hospitalization;

    private LocalDate recordDate;
    private String observations;
    private String diagnosis;

    public MedicalEvolution(MedicalEvolutionDto dto) {
        this.id = dto.getId();
        this.hospitalization = dto.getHospitalization() != null ? new Hospitalization(dto.getHospitalization()) : null;
        this.recordDate = dto.getRecordDate();
        this.observations = dto.getObservations();
        this.diagnosis = dto.getDiagnosis();
    }

    public MedicalEvolutionDto toAggregate() {
        return new MedicalEvolutionDto(id, hospitalization.toAggregate(), recordDate, observations, diagnosis);
    }
}
