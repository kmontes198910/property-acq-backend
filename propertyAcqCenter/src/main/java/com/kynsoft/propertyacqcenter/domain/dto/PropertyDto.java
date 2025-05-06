package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.*;

@Builder
@Setter
@Getter
public class PropertyDto {

    private String id;
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
}