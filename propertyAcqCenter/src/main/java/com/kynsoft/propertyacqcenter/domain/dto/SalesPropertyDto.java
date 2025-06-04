package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SalesPropertyDto implements Serializable {

    private UUID id;
    private PropertyDto property;

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
