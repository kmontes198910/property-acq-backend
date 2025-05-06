package com.kynsoft.propertyacqcenter.application.command.property.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePropertyCommand implements ICommand {

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

    public CreatePropertyCommand(String id, PropertyType propertyType, int lotSize, 
                                 String apn, int yearBuilt, String county, Boolean occupancy, 
                                 String addressLine1, String addressLine2, String city, String state, 
                                 String zipCode, double unitCount, int squareFootage) {
        this.id = id;
        this.propertyType = propertyType;
        this.lotSize = lotSize;
        this.apn = apn;
        this.yearBuilt = yearBuilt;
        this.county = county;
        this.occupancy = occupancy;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.unitCount = unitCount;
        this.squareFootage = squareFootage;
    }

    public static CreatePropertyCommand fromRequest(CreatePropertyRequest request) {
        return new CreatePropertyCommand(
                request.getId(),
                request.getPropertyType(),
                request.getLotSize(),
                request.getApn(),
                request.getYearBuilt(),
                request.getCounty(),
                request.getOccupancy(),
                request.getAddressLine1(),
                request.getAddressLine2(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getUnitCount(),
                request.getSquareFootage()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePropertyMessage(id);
    }
}
