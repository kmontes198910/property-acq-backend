package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyRequest {

    private PropertyType propertyType;
    private int lotSize;
    private String apn;
    private int yearBuilt;
    private String county;
    private Boolean occupancy;
    //Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;

    private double unitCount;
    private int squareFootage;

    private String roofType;
    private String structureType;
    private String hoa;
    private Integer bedrooms;
    private Double bathrooms;
    private Double askingPrice;
    private String formattedAddress;
    private PropertyStatus propertyStatus;
    private Boolean isManual;
    private Integer daysOnMarket;

    private Double purchasePrice;
    private Double rentalPrice;
    private Double afterRepairValue;
    private Boolean floodZoneDetermination;
    private Boolean propertyRented;

}
