package com.kynsoft.propertyacqcenter.domain.dto.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDto {

    private UUID id;
    private Integer estimatedValuePrice;
    private Integer estimatedValuePriceRangeLow;
    private Integer estimatedValuePriceRangeHigh;
    private Integer estimatedValueAveragePrice;
    private AnalysisDto analysis;
}
