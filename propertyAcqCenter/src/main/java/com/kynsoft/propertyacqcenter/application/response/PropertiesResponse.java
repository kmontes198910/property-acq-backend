package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.RoofType;
import com.kynsoft.propertyacqcenter.domain.enums.StructureType;
import lombok.*;

@Setter
@Getter
public class PropertiesResponse implements IResponse {

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

    private RoofType roofType;
    private StructureType structureType;
    private String hoa;
    private Integer bedrooms;
    private Double bathrooms;
    private Double askingPrice;

    public PropertiesResponse(PropertyDto dto) {
        this.id = dto.getId();
        this.propertyType = dto.getPropertyType();
        this.lotSize = dto.getLotSize();
        this.apn = dto.getApn();
        this.yearBuilt = dto.getYearBuilt();
        this.county = dto.getCounty();
        this.occupancy = dto.getOccupancy();
        this.addressLine1 = dto.getAddressLine1();
        this.addressLine2 = dto.getAddressLine2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.unitCount = dto.getUnitCount();
        this.squareFootage = dto.getSquareFootage();
        this.roofType = dto.getRoofType();
        this.structureType = dto.getStructureType();
        this.hoa = dto.getHoa();
        this.bedrooms = dto.getBedrooms();
        this.bathrooms = dto.getBathrooms();
        this.askingPrice = dto.getAskingPrice();
    }

}