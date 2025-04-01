package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.HospitalDischargeSummaryDto;
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
@Table(name = "hospital_discharge_summary")
public class HospitalDischargeSummary {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "hospitalization_id", nullable = false)
    private Hospitalization hospitalization;

    private LocalDate dischargeDate;
    private String finalDiagnosis;
    private String treatmentsPerformed;
    private String recommendations;

    public HospitalDischargeSummary(HospitalDischargeSummaryDto dto) {
        this.id = dto.getId();
        this.hospitalization = dto.getHospitalization() != null ? new Hospitalization(dto.getHospitalization()) : null;
        this.dischargeDate = dto.getDischargeDate();
        this.finalDiagnosis = dto.getFinalDiagnosis();
        this.treatmentsPerformed = dto.getTreatmentsPerformed();
        this.recommendations = dto.getRecommendations();
    }

    public HospitalDischargeSummaryDto toAggregate() {
        return new HospitalDischargeSummaryDto(
                id, 
                hospitalization.toAggregate(), 
                dischargeDate, 
                finalDiagnosis, 
                treatmentsPerformed, 
                recommendations
        );
    }

}
