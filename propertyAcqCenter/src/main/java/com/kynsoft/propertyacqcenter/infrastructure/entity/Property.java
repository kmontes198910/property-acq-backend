package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.RoofType;
import com.kynsoft.propertyacqcenter.domain.enums.StructureType;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "properties")
public class Property {

    @Id
    private String id;
    private String formattedAddress;

    @Enumerated(EnumType.STRING)
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

    public Property(PropertyDto dto) {
        this.id = dto.getId();
        this.formattedAddress = dto.getFormattedAddress();
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

    public PropertyDto toAggregate() {
        return PropertyDto.builder()
                .id(this.id)
                .formattedAddress(formattedAddress)
                .addressLine1(addressLine1)
                .addressLine2(addressLine2)
                .apn(apn)
                .city(city)
                .county(county)
                .lotSize(lotSize)
                .occupancy(occupancy)
                .propertyType(propertyType)
                .squareFootage(squareFootage)
                .state(state)
                .unitCount(unitCount)
                .yearBuilt(yearBuilt)
                .zipCode(zipCode)
                .roofType(roofType)
                .structureType(structureType)
                .hoa(hoa)
                .bedrooms(bedrooms)
                .bathrooms(bathrooms)
                .askingPrice(askingPrice)
                .build();
    }

}