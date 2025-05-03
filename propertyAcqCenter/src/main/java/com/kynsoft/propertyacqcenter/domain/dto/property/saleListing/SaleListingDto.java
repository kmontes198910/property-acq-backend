package com.kynsoft.propertyacqcenter.domain.dto.property.saleListing;

import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.HoaDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.Status;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleListingDto {

    private String id;
    private String formattedAddress;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private double latitude;
    private double longitude;
    private PropertyType propertyType;
    private int bedrooms;
    private int bathrooms;
    private int squareFootage;
    private int lotSize;
    private int yearBuilt;
    private HoaDto hoa;
    private Status status;
    private double price;
    private String listingType;
    private String listedDate;
    private String removedDate;
    private String createdDate;
    private String lastSeenDate;
    private int daysOnMarket;
    private String mlsName;
    private String mlsNumber;
    private ListingAgentDto listingAgent;
    private ListingOfficeDto listingOffice;
    private Map<String, ListingHistoryDto> history;
}
