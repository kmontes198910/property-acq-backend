package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SalesPropertyResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;

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

    public SalesPropertyResponse(SalesPropertyDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.capitalGainsTaxRate = dto.getCapitalGainsTaxRate();
        this.stateIncomeTaxRate = dto.getStateIncomeTaxRate();
        this.federalIncomeTaxRate = dto.getFederalIncomeTaxRate();
        this.purchesePrice = dto.getPurchesePrice();
        this.marketValueIndreaseRate = dto.getMarketValueIndreaseRate();
        this.isPurchesePrice = dto.getIsPurchesePrice();
        this.isMarketValueIndreaseRate = dto.getIsMarketValueIndreaseRate();
        this.isInflationRate = dto.getIsInflationRate();
        this.isMarketValue = dto.getIsMarketValue();
        this.isCapRate = dto.getIsCapRate();
        this.isFixedSellingPrice = dto.getIsFixedSellingPrice();
        this.other = dto.getOther();
        this.salesCostNone = dto.getSalesCostNone();
        this.salesCostPercentage = dto.getSalesCostPercentage();
        this.salesCostFixedDollarAmount = dto.getSalesCostFixedDollarAmount();
        this.deprecationNone = dto.getDeprecationNone();
        this.deprecationStraightline = dto.getDeprecationStraightline();
        this.deprecationDoubleDecliningBalance = dto.getDeprecationDoubleDecliningBalance();
    }

}
