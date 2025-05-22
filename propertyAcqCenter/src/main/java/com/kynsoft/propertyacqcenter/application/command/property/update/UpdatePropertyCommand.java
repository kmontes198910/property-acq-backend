package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.RoofType;
import com.kynsoft.propertyacqcenter.domain.enums.StructureType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyCommand implements ICommand {

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
    private String formattedAddress;
    private PropertyStatus propertyStatus;

    public UpdatePropertyCommand(String id, PropertyType propertyType, int lotSize, 
                                 String apn, int yearBuilt, String county, Boolean occupancy, 
                                 String addressLine1, String addressLine2, String city, String state, 
                                 String zipCode, double unitCount, int squareFootage,
                                 RoofType roofType, StructureType structureType, String hoa,
                                 Integer bedrooms, Double bathrooms, Double askingPrice,
                                 String formattedAddress, PropertyStatus propertyStatus) {
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
        this.roofType = roofType;
        this.structureType = structureType;
        this.hoa = hoa;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.askingPrice = askingPrice;
        this.formattedAddress = formattedAddress;
        this.propertyStatus = propertyStatus;
    }

    public static UpdatePropertyCommand fromRequest(UpdatePropertyRequest request, String id) {
        return new UpdatePropertyCommand(
                id,
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
                request.getSquareFootage(),
                request.getRoofType(),
                request.getStructureType(),
                request.getHoa(),
                request.getBedrooms(),
                request.getBathrooms(),
                request.getAskingPrice(),
                request.getFormattedAddress(),
                request.getPropertyStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyMessage(id);
    }
}
