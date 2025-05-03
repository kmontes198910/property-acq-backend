package com.kynsoft.propertyacqcenter.application.response.estimateValue;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.dto.ComparablePropertyDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparablePropertyResponse implements IResponse {

    private UUID id;
    //private UUID estimateValue;
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

    public ComparablePropertyResponse(ComparablePropertyDto dto) {
        this.id = dto.getId();
        //this.estimateValue = dto.;
        this.formattedAddress = dto.getFormattedAddress();
        this.addressLine1 = dto.getAddressLine1();
        this.addressLine2 = dto.getAddressLine2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.county = dto.getCounty();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.propertyType = dto.getPropertyType();
        this.bedrooms = dto.getBedrooms();
        this.bathrooms = dto.getBathrooms();
        this.squareFootage = dto.getSquareFootage();
        this.lotSize = dto.getLotSize();
        this.yearBuilt = dto.getYearBuilt();
        this.price = dto.getPrice();
        this.listingType = dto.getListingType();
        this.listedDate = dto.getListedDate();
        this.removedDate = dto.getRemovedDate();
        this.lastSeenDate = dto.getLastSeenDate();
        this.daysOnMarket = dto.getDaysOnMarket();
        this.distance = dto.getDistance();
        this.daysOld = dto.getDaysOld();
        this.correlation = dto.getCorrelation();
    }

}
