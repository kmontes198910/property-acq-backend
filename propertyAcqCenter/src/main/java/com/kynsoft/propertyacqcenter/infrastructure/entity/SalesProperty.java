package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertysAnnualValueIncrease;
import com.kynsoft.propertyacqcenter.domain.enums.PropertysStarting;
import com.kynsoft.propertyacqcenter.domain.enums.TypeOfSalesCost;
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

    //Property's starting value
    @Enumerated(EnumType.STRING)
    @Column(name = "propertys_starting", nullable = true)
    private PropertysStarting propertysStarting;

    //Property's annual value increase
    @Enumerated(EnumType.STRING)
    @Column(name = "propertys_annual_value_increase", nullable = true)
    private PropertysAnnualValueIncrease propertysAnnualValueIncrease;

    //Type of sales cost
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_sales_cost", nullable = true)
    private TypeOfSalesCost typeOfSalesCost;

    private Boolean deprecationNone;
    private Boolean deprecationStraightline;
    private Boolean deprecationDoubleDecliningBalance;

    private Double marketValueIndreaseRate;

    public SalesProperty(SalesPropertyDto dto) {
        this.id = dto.getId();
        this.propertysStarting = dto.getPropertysStarting();
        this.propertysAnnualValueIncrease = dto.getPropertysAnnualValueIncrease();
        this.typeOfSalesCost = dto.getTypeOfSalesCost();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.capitalGainsTaxRate = dto.getCapitalGainsTaxRate();
        this.stateIncomeTaxRate = dto.getStateIncomeTaxRate();
        this.federalIncomeTaxRate = dto.getFederalIncomeTaxRate();
        this.purchesePrice = dto.getPurchesePrice();
        this.marketValueIndreaseRate = dto.getMarketValueIndreaseRate();
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
                .deprecationNone(deprecationNone)
                .deprecationStraightline(deprecationStraightline)
                .deprecationDoubleDecliningBalance(deprecationDoubleDecliningBalance)
                .propertysStarting(propertysStarting)
                .propertysAnnualValueIncrease(propertysAnnualValueIncrease)
                .typeOfSalesCost(typeOfSalesCost)
                .build();
    }
}
