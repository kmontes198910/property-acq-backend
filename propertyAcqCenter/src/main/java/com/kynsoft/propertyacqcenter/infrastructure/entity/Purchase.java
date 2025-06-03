package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PurchaseDto;
import com.kynsoft.propertyacqcenter.domain.enums.ForeclosureStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "purchase")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Purchase implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private ForeclosureStatus foreclosureStatus;

    private String improvements;//TODO: por definir
    private Double purchasePrice;
    private Double estimatedMarketValue;

    public Purchase(PurchaseDto dto) {
        this.id = dto.getId();
        this.propertyType = dto.getPropertyType();
        this.foreclosureStatus = dto.getForeclosureStatus();
        this.improvements = dto.getImprovements();
        this.purchasePrice = dto.getPurchasePrice();
        this.estimatedMarketValue = dto.getEstimatedMarketValue();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
    }

    public PurchaseDto toAggregate() {
        return PurchaseDto.builder()
                .id(this.id)
                .property(property.toAggregateBasic())
                .propertyType(propertyType)
                .foreclosureStatus(foreclosureStatus)
                .improvements(improvements)
                .purchasePrice(purchasePrice)
                .estimatedMarketValue(estimatedMarketValue)
                .build();
    }
}
