package com.kynsoft.propertyacqcenter.application.command.analysis.create;

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
public class StatisticsRequest {

    private Integer estimatedValuePrice;
    private Integer estimatedValuePriceRangeLow;
    private Integer estimatedValuePriceRangeHigh;
    private Integer estimatedValueAveragePrice;
}
