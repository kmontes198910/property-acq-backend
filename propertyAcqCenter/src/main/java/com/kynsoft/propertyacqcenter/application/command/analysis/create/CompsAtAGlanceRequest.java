package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompsAtAGlanceRequest {
    private Double avgSalePrice;
    private Integer dayOnMarket;
}
