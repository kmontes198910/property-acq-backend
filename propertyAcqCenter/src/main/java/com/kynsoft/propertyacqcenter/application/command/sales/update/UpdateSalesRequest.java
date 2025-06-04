package com.kynsoft.propertyacqcenter.application.command.sales.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSalesRequest {

    private String property;
    private Double capitalGainsTaxRate;
    private Double stateIncomeTaxRate;
    private Double federalIncomeTaxRate;
    private Double purchesePrice;
    private Double marketValueIndreaseRate;
    private Boolean isPurchesePrice;
    private Boolean isMarketValueIndreaseRate;
    private Boolean isInflationRate;
    private Boolean isMarketValue;
    private Boolean isCapRate;
    private Boolean isFixedSellingPrice;
    private Boolean other;
    private Boolean salesCostNone;
    private Boolean salesCostPercentage;
    private Boolean salesCostFixedDollarAmount;
    private Boolean deprecationNone;
    private Boolean deprecationStraightline;
    private Boolean deprecationDoubleDecliningBalance;

}
