package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
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
public class DashboardStatisticsResponse implements IResponse {
    private Integer estimatedValuePrice;
    private Integer estimatedValuePriceRangeLow;
    private Integer estimatedValuePriceRangeHigh;
    private Integer estimatedValueAveragePrice;

    private Integer estimatedRentValuePrice;
    private Integer estimatedRentValuePriceRangeLow;
    private Integer estimatedRentValuePriceRangeHigh;
    private Integer estimatedRentValueAveragePrice;
}
