package com.kynsoft.propertyacqcenter.application.command.purchase.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.ForeclosureStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePurchaseCommand implements ICommand {

    private UUID id;
    private String property;
    private PropertyType propertyType;
    private ForeclosureStatus foreclosureStatus;
    private Double improvements;
    private Double purchasePrice;
    private Double estimatedMarketValue;

    public CreatePurchaseCommand(String property, PropertyType propertyType, 
                                 ForeclosureStatus foreclosureStatus, Double improvements, 
                                 Double purchasePrice, Double estimatedMarketValue) {
        this.id = UUID.randomUUID();
        this.property = property;
        this.propertyType = propertyType;
        this.foreclosureStatus = foreclosureStatus;
        this.improvements = improvements;
        this.purchasePrice = purchasePrice;
        this.estimatedMarketValue = estimatedMarketValue;
    }

    public static CreatePurchaseCommand fromRequest(CreatePurchaseRequest request) {
        return new CreatePurchaseCommand(
                request.getProperty(),
                request.getPropertyType(),
                request.getForeclosureStatus(),
                request.getImprovements(),
                request.getPurchasePrice(),
                request.getEstimatedMarketValue()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePurchaseMessage(id);
    }
}
