package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.SaleValueDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sale_value")
public class SaleValue {
    @Id
    private UUID id;

    private Double estimatedValue;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    public SaleValue(SaleValueDto dto) {
        this.id = dto.getId();
        this.estimatedValue = dto.getEstimatedValue();
        this.lastYear = dto.getLastYear();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public SaleValueDto toAggregate() {
        return SaleValueDto.builder()
                .id(this.id)
                .estimatedValue(estimatedValue)
                .lastYear(lastYear)
                .build();
    }

}
