package com.kynsoft.propertyacqcenter.application.command.sales.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateSalesCommand implements ICommand {

    private UUID id;
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

    public CreateSalesCommand(String property, Double capitalGainsTaxRate, Double stateIncomeTaxRate, Double federalIncomeTaxRate, Double purchesePrice, Double marketValueIndreaseRate, Boolean isPurchesePrice, Boolean isMarketValueIndreaseRate, Boolean isInflationRate, Boolean isMarketValue, Boolean isCapRate, Boolean isFixedSellingPrice, Boolean other, Boolean salesCostNone, Boolean salesCostPercentage, Boolean salesCostFixedDollarAmount, Boolean deprecationNone, Boolean deprecationStraightline, Boolean deprecationDoubleDecliningBalance) {
        this.id = UUID.randomUUID();
        this.property = property;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.stateIncomeTaxRate = stateIncomeTaxRate;
        this.federalIncomeTaxRate = federalIncomeTaxRate;
        this.purchesePrice = purchesePrice;
        this.marketValueIndreaseRate = marketValueIndreaseRate;
        this.isPurchesePrice = isPurchesePrice;
        this.isMarketValueIndreaseRate = isMarketValueIndreaseRate;
        this.isInflationRate = isInflationRate;
        this.isMarketValue = isMarketValue;
        this.isCapRate = isCapRate;
        this.isFixedSellingPrice = isFixedSellingPrice;
        this.other = other;
        this.salesCostNone = salesCostNone;
        this.salesCostPercentage = salesCostPercentage;
        this.salesCostFixedDollarAmount = salesCostFixedDollarAmount;
        this.deprecationNone = deprecationNone;
        this.deprecationStraightline = deprecationStraightline;
        this.deprecationDoubleDecliningBalance = deprecationDoubleDecliningBalance;
    }

    public static CreateSalesCommand fromRequest(CreateSalesRequest request) {
        return new CreateSalesCommand(
                request.getProperty(),
                request.getCapitalGainsTaxRate(),
                request.getStateIncomeTaxRate(),
                request.getFederalIncomeTaxRate(),
                request.getPurchesePrice(),
                request.getMarketValueIndreaseRate(),
                request.getIsPurchesePrice(),
                request.getIsMarketValueIndreaseRate(),
                request.getIsInflationRate(),
                request.getIsMarketValue(),
                request.getIsCapRate(),
                request.getIsFixedSellingPrice(),
                request.getOther(),
                request.getSalesCostNone(),
                request.getSalesCostPercentage(),
                request.getSalesCostFixedDollarAmount(),
                request.getDeprecationNone(),
                request.getDeprecationStraightline(),
                request.getDeprecationDoubleDecliningBalance()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateSalesMessage(id);
    }
}
