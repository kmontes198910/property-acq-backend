package com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.dto;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparablePropertyDto {

    private UUID id;
    private UUID estimateValue;
    private String formattedAddress;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private Double latitude;
    private Double longitude;
    private PropertyType propertyType;
    private Integer bedrooms;
    private Double bathrooms;
    private Integer squareFootage;
    private Integer lotSize;
    private Integer yearBuilt;
    private Integer price;
    private String listingType;
    private String listedDate;
    private String removedDate;
    private String lastSeenDate;
    private Integer daysOnMarket;
    private Double distance;
    private Integer daysOld;
    private Double correlation;
}
