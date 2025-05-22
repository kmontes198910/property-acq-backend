package com.kynsoft.propertyacqcenter.application.response.dashboardInfo;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DashboardPropertyResponse implements IResponse {
    private int lotSize;
    private String apn;
    private String id;
    private int yearBuilt;
    private String county;
    //private PropertyType propertyType;    
    private String propertyType;    
    private Boolean occupancy;

    //Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private Double latitude;
    private Double longitude;

    private double unitCount;
    private int squareFootage;

    private String distressed;
    private String shortSale;
    private String hoa;
    private String ownerStatus;
    private String lengthOfOwnership;
    private String purchaseMethod;
    private String status;

    private Integer bedrooms;
    private Integer bathrooms;
    private String roofType;
    private String structureType;

    private Boolean garage;
    private Integer garageSpaces;
    private String garageType;

    private Boolean pool;
    private String poolType;

    private DashboardPropertyOwnerResponse owners;

    private String subdivision;
    private String zoning;
    private String lastSaleDate;
    private boolean ownerOccupied;
}
