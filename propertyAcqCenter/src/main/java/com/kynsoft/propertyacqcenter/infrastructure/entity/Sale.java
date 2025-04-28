package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.SaleDto;
import com.kynsoft.propertyacqcenter.domain.enums.ListingType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    private Double price;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    private LocalDateTime listedDate;
    private LocalDateTime removedDate;
    private int daysOnMarket;
    private String mlsName;
    private String mlsNumber;

    @Embedded
    private ListingAgent listingAgent;

    @Embedded
    private PropertyBuilder propertyBuilder;

    public Sale(SaleDto dto) {
        this.price = dto.getPrice();
        this.listingType = dto.getListingType();
        this.listedDate = dto.getListedDate();
        this.removedDate = dto.getRemovedDate();
        this.daysOnMarket = dto.getDaysOnMarket();
        this.mlsName = dto.getMlsName();
        this.mlsNumber = dto.getMlsNumber();
        this.listingAgent = dto.getListingAgent() != null ? new ListingAgent(dto.getListingAgent()) : null;
        this.propertyBuilder = dto.getPropertyBuilder() != null ? new PropertyBuilder(dto.getPropertyBuilder()) : null;
    }

    public SaleDto toAggregate() {
        return SaleDto.builder()
                .price(price)
                .listingType(listingType)
                .listedDate(listedDate)
                .removedDate(removedDate)
                .daysOnMarket(daysOnMarket)
                .mlsName(mlsName)
                .mlsNumber(mlsNumber)
                .listingAgent(listingAgent != null ? listingAgent.toAggregate() : null)
                .propertyBuilder(propertyBuilder != null ? propertyBuilder.toAggregate() : null)
                .build();
    }
}