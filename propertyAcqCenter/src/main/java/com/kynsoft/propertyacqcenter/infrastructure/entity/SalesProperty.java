package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "sale")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SalesProperty implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

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

    public SalesProperty(SalesPropertyDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
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

    public SalesPropertyDto toAggregate() {
        return SalesPropertyDto.builder()
                .id(this.id)
                .property(property.toAggregateBasic())
                .capitalGainsTaxRate(capitalGainsTaxRate)
                .stateIncomeTaxRate(stateIncomeTaxRate)
                .federalIncomeTaxRate(federalIncomeTaxRate)
                .purchesePrice(purchesePrice)
                .marketValueIndreaseRate(marketValueIndreaseRate)
                .isPurchesePrice(isPurchesePrice)
                .isMarketValueIndreaseRate(isMarketValueIndreaseRate)
                .isInflationRate(isInflationRate)
                .isMarketValue(isMarketValue)
                .isCapRate(isCapRate)
                .isFixedSellingPrice(isFixedSellingPrice)
                .other(other)
                .salesCostNone(salesCostNone)
                .salesCostPercentage(salesCostPercentage)
                .salesCostFixedDollarAmount(salesCostFixedDollarAmount)
                .deprecationNone(deprecationNone)
                .deprecationStraightline(deprecationStraightline)
                .deprecationDoubleDecliningBalance(deprecationDoubleDecliningBalance)
                .build();
    }
}
