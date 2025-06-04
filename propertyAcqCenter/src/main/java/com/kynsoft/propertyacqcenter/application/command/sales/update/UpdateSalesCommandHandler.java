package com.kynsoft.propertyacqcenter.application.command.sales.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateSalesCommandHandler implements ICommandHandler<UpdateSalesCommand> {

    private final ISalesPropertyService salesPropertyService;
    private final IPropertyService propertyService;

    public UpdateSalesCommandHandler(ISalesPropertyService salesPropertyService, IPropertyService propertyService) {
        this.salesPropertyService = salesPropertyService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateSalesCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.salesPropertyService.create(SalesPropertyDto.builder()
                .id(command.getId())
                .property(property)
                .capitalGainsTaxRate(command.getCapitalGainsTaxRate())
                .stateIncomeTaxRate(command.getStateIncomeTaxRate())
                .federalIncomeTaxRate(command.getFederalIncomeTaxRate())
                .purchesePrice(command.getPurchesePrice())
                .marketValueIndreaseRate(command.getMarketValueIndreaseRate())
                .isPurchesePrice(command.getIsPurchesePrice())
                .isMarketValueIndreaseRate(command.getIsMarketValueIndreaseRate())
                .isInflationRate(command.getIsInflationRate())
                .isMarketValue(command.getIsMarketValue())
                .isCapRate(command.getIsCapRate())
                .isFixedSellingPrice(command.getIsFixedSellingPrice())
                .other(command.getOther())
                .salesCostNone(command.getSalesCostNone())
                .salesCostPercentage(command.getSalesCostPercentage())
                .salesCostFixedDollarAmount(command.getSalesCostFixedDollarAmount())
                .deprecationNone(command.getDeprecationNone())
                .deprecationStraightline(command.getDeprecationStraightline())
                .deprecationDoubleDecliningBalance(command.getDeprecationDoubleDecliningBalance())
                .build()
        );
    }

}
