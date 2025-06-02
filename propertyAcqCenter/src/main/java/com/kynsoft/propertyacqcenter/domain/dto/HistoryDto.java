package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.ListingType;
import com.kynsoft.propertyacqcenter.domain.enums.EventType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDto {

    private UUID id;
    private EventType event;
    private Double price;
    private ListingType listingType;
    private LocalDateTime listingDate;
    private int daysOnMarket;
    private UUID property;
}
