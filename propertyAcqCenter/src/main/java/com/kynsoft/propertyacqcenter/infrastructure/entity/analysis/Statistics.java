package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.StatisticsDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    @Id
    private UUID id;

    private Integer estimatedValuePrice;
    private Integer estimatedValuePriceRangeLow;
    private Integer estimatedValuePriceRangeHigh;
    private Integer estimatedValueAveragePrice;

    @OneToOne(mappedBy = "statistics")
    private Analysis analysis;

    public Statistics(StatisticsDto dto) {
        this.id = dto.getId();
        this.estimatedValuePrice = dto.getEstimatedValuePrice();
        this.estimatedValuePriceRangeLow = dto.getEstimatedValuePriceRangeLow();
        this.estimatedValuePriceRangeHigh = dto.getEstimatedValuePriceRangeHigh();
        this.estimatedValueAveragePrice = dto.getEstimatedValueAveragePrice();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public StatisticsDto toAggregate() {
        return StatisticsDto.builder()
                .id(this.id)
                .estimatedValuePrice(estimatedValuePrice)
                .estimatedValuePriceRangeLow(estimatedValuePriceRangeLow)
                .estimatedValuePriceRangeHigh(estimatedValuePriceRangeHigh)
                .estimatedValueAveragePrice(estimatedValueAveragePrice)
                .build();
    }

}
