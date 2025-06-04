package com.kynsoft.propertyacqcenter.domain.dto;

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
public class PurchaseDto implements Serializable {

    private UUID id;
    private PropertyDto property;
    private PropertyType propertyType;
    private ForeclosureStatus foreclosureStatus;

    private Double improvements;
    private Double purchasePrice;
    private Double estimatedMarketValue;
}