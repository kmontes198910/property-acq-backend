package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnalysisRequest {
    private String property;
    private OpportunityRequest opportunity;
    private StatisticsRequest statistics;
    private MortageDebtRequest mortageDebt;
    private CompsAtAGlanceRequest compsAtAGlance;
    private LastSaleRequest lastSale;
    private List<SaleValueRequest> saleValue;
    private List<TaxAssessmentAnalysisRequest> taxAssessments;
    private List<PropertyComparableRequest> comparables;
}
