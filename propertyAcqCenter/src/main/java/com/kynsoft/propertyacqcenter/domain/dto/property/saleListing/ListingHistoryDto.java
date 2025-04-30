package com.kynsoft.propertyacqcenter.domain.dto.property.saleListing;

import java.time.LocalDateTime;
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
    private LocalDateTime listedDate;
    private LocalDateTime removedDate;
    private int daysOnMarket;
}
