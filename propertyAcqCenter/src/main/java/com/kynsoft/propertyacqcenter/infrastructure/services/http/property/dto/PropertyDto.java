package com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {

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
    private String assessorID;
    private String legalDescription;
    private String subdivision;
    private String zoning;
    private String lastSaleDate;//LocalDateTime
    private double lastSalePrice;
    private HoaDto hoa;
    private FeaturesDto features;
    private Map<String, TaxAssessmentsDto> taxAssessments;
    private Map<String, PropertyTaxDto> propertyTaxes;
    private Map<String, SaleHistoryDto> history;
    private OwnerDto owner;
    private boolean ownerOccupied;
}
