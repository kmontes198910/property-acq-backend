package com.kynsoft.propertyacqcenter.application.command.purchase.create;

import com.kynsoft.propertyacqcenter.domain.enums.ForeclosureStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePurchaseRequest {

    private String property;
    private PropertyType propertyType;
    private ForeclosureStatus foreclosureStatus;
    private String improvements;
    private Double purchasePrice;
    private Double estimatedMarketValue;

}
