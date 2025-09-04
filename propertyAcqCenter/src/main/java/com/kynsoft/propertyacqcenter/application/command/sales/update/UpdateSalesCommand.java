package com.kynsoft.propertyacqcenter.application.command.sales.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.PropertysAnnualValueIncrease;
import com.kynsoft.propertyacqcenter.domain.enums.PropertysStarting;
import com.kynsoft.propertyacqcenter.domain.enums.TypeOfSalesCost;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateSalesCommand implements ICommand {

    private UUID id;
    private String property;
    private Double capitalGainsTaxRate;
    private Double stateIncomeTaxRate;
    private Double federalIncomeTaxRate;
    private Double purchesePrice;
    private Double marketValueIndreaseRate;
    private Boolean deprecationNone;
    private Boolean deprecationStraightline;
    private Boolean deprecationDoubleDecliningBalance;

    private PropertysStarting propertysStarting;
    private PropertysAnnualValueIncrease propertysAnnualValueIncrease;
    private TypeOfSalesCost typeOfSalesCost;
    private Double afterRepairValue;

    public UpdateSalesCommand(UUID id, String property, Double capitalGainsTaxRate, Double stateIncomeTaxRate, 
            Double federalIncomeTaxRate, Double purchesePrice, Double marketValueIndreaseRate, Boolean deprecationNone, Boolean deprecationStraightline, Boolean deprecationDoubleDecliningBalance,
            PropertysStarting propertysStarting, PropertysAnnualValueIncrease propertysAnnualValueIncrease, 
            TypeOfSalesCost typeOfSalesCost, Double afterRepairValue) {
        this.id = id;
        this.property = property;
        this.propertysStarting = propertysStarting;
        this.propertysAnnualValueIncrease = propertysAnnualValueIncrease;
        this.typeOfSalesCost = typeOfSalesCost;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.stateIncomeTaxRate = stateIncomeTaxRate;
        this.federalIncomeTaxRate = federalIncomeTaxRate;
        this.purchesePrice = purchesePrice;
        this.marketValueIndreaseRate = marketValueIndreaseRate;
        this.deprecationNone = deprecationNone;
        this.deprecationStraightline = deprecationStraightline;
        this.deprecationDoubleDecliningBalance = deprecationDoubleDecliningBalance;
        this.afterRepairValue = afterRepairValue;
    }

    public static UpdateSalesCommand fromRequest(UpdateSalesRequest request, UUID id) {
        return new UpdateSalesCommand(
                id,
                request.getProperty(),
                request.getCapitalGainsTaxRate(),
                request.getStateIncomeTaxRate(),
                request.getFederalIncomeTaxRate(),
                request.getPurchesePrice(),
                request.getMarketValueIndreaseRate(),
                request.getDeprecationNone(),
                request.getDeprecationStraightline(),
                request.getDeprecationDoubleDecliningBalance(),
                request.getPropertysStarting(),
                request.getPropertysAnnualValueIncrease(),
                request.getTypeOfSalesCost(),
                request.getAfterRepairValue()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSalesMessage(id);
    }
}
