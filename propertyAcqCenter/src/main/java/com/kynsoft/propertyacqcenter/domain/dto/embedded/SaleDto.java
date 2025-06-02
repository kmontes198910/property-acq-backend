package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import com.kynsoft.propertyacqcenter.domain.enums.ListingType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDto {

    private Double price;
    private ListingType listingType;
    private LocalDateTime listedDate;
    private LocalDateTime removedDate;
    private int daysOnMarket;
    private String mlsName;
    private String mlsNumber;
    private ListingAgentDto listingAgent;
    private PropertyBuilderDto propertyBuilder;
}
