package com.kynsoft.propertyacqcenter.application.command.sales.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreateSalesCommandHandler implements ICommandHandler<CreateSalesCommand> {

    private final ISalesPropertyService salesPropertyService;
    private final IPropertyService propertyService;

    public CreateSalesCommandHandler(ISalesPropertyService salesPropertyService, IPropertyService propertyService) {
        this.salesPropertyService = salesPropertyService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateSalesCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.salesPropertyService.create(SalesPropertyDto.builder()
                .id(command.getId())
                .property(property)
                .capitalGainsTaxRate(command.getCapitalGainsTaxRate())
                .stateIncomeTaxRate(command.getStateIncomeTaxRate())
                .federalIncomeTaxRate(command.getFederalIncomeTaxRate())
                .purchesePrice(command.getPurchesePrice())
                .marketValueIndreaseRate(command.getMarketValueIndreaseRate())
                .deprecationNone(command.getDeprecationNone())
                .deprecationStraightline(command.getDeprecationStraightline())
                .deprecationDoubleDecliningBalance(command.getDeprecationDoubleDecliningBalance())
                .propertysStarting(command.getPropertysStarting())
                .propertysAnnualValueIncrease(command.getPropertysAnnualValueIncrease())
                .typeOfSalesCost(command.getTypeOfSalesCost())
                .build()
        );
    }

}
