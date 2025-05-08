package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.TaxAssessmentAnalysisDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "tax_assessment_analysis")
public class TaxAssessmentAnalysis {
    @Id
    private UUID id;

    private Integer year;
    private Double value;
    private Double land;
    private Double improvements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    public TaxAssessmentAnalysis(TaxAssessmentAnalysisDto dto) {
        this.id = dto.getId();
        this.year = dto.getYear();
        this.value = dto.getValue();
        this.land = dto.getLand();
        this.improvements = dto.getImprovements();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public TaxAssessmentAnalysisDto toAggregate() {
        return TaxAssessmentAnalysisDto.builder()
                .id(this.id)
                .improvements(improvements)
                .land(land)
                .value(value)
                .build();
    }

}
