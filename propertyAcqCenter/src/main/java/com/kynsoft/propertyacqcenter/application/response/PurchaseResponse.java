package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.ForeclosureStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;
    private PropertyType propertyType;
    private ForeclosureStatus foreclosureStatus;

    private Double improvements;
    private Double purchasePrice;
    private Double estimatedMarketValue;

    public PurchaseResponse(PurchaseDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.propertyType = dto.getPropertyType();
        this.foreclosureStatus = dto.getForeclosureStatus();
        this.improvements = dto.getImprovements();
        this.purchasePrice = dto.getPurchasePrice();
        this.estimatedMarketValue = dto.getEstimatedMarketValue();
    }

}