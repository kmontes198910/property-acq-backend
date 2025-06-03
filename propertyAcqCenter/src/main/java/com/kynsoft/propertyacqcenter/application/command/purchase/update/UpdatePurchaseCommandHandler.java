package com.kynsoft.propertyacqcenter.application.command.purchase.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PurchaseDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class UpdatePurchaseCommandHandler implements ICommandHandler<UpdatePurchaseCommand> {

    private final IPurchaseService purchaseService;
    private final IPropertyService propertyService;

    public UpdatePurchaseCommandHandler(IPurchaseService purchaseService, IPropertyService propertyService) {
        this.purchaseService = purchaseService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdatePurchaseCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.purchaseService.update(PurchaseDto.builder()
                .id(command.getId())
                .property(property)
                .propertyType(command.getPropertyType())
                .estimatedMarketValue(command.getEstimatedMarketValue())
                .foreclosureStatus(command.getForeclosureStatus())
                .improvements(command.getImprovements())
                .purchasePrice(command.getPurchasePrice())
                .build()
        );
    }

}
