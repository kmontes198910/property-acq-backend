package com.kynsoft.propertyacqcenter.domain.dto.property.saleListing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListingHistoryDto {
    private String event;
    private double price;
    private String listingType;
    private String listedDate;
    private String removedDate;
    private int daysOnMarket;
}
