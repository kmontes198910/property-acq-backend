package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import java.time.LocalDate;
import java.util.UUID;
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

    private LocalDate contractExecutionDate;
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.
    private UUID sellerName;//Relacion con Legal entity
    private UUID sellerContactInfo;//Relacion con Company
    private LocalDate expectedClosingDate;
    private Boolean emdRequirements;
    private Double emdOfferedAmount;

    //Mortagage
    private Boolean isMortgage;//
    private Boolean distressed;//
    private String lenghOfOwership;//
    private Double openBalanceMortagage;//
    private Double involuntaryLiensAmount;//

    //last sale
    private Double publicRecord;//
    private Double mls;//

    private Double buildingArea;
    private Double livingArea;
    private Double grossArea;
    private Double taxableArea;
    private Double garageArea;

    private Double closingCost;//

    public UpdatePropertyCommand(String id, PropertyType propertyType, int lotSize, 
                                 String apn, int yearBuilt, String county, Boolean occupancy, 
                                 String addressLine1, String addressLine2, String city, String state, 
                                 String zipCode, double unitCount, int squareFootage,
                                 String roofType, String structureType, String hoa,
                                 Integer bedrooms, Double bathrooms, Double askingPrice,
                                 String formattedAddress, PropertyStatus propertyStatus,
                                 Boolean isManual, Integer daysOnMarket,
                                 Double purchasePrice, Double rentalPrice, Double afterRepairValue, Boolean floodZoneDetermination,
                                 Boolean propertyRented,
                                 LocalDate contractExecutionDate, AcquisitionType acquisitionType, SourceType sourceType,
                                 UUID sellerName, UUID sellerContactInfo, LocalDate expectedClosingDate,
                                 Boolean emdRequirements, Double emdOfferedAmount,
                                 Boolean distressed, String lenghOfOwership, Double openBalanceMortagage, Double involuntaryLiensAmount,
                                 Double publicRecord, Double mls, Boolean isMortgage,
                                 Double buildingArea, Double livingArea, Double grossArea, Double taxableArea, Double garageArea,
                                 Double closingCost) {
        this.id = id;
        this.buildingArea = buildingArea;
        this.taxableArea = taxableArea;
        this.garageArea = garageArea;
        this.grossArea = grossArea;
        this.livingArea = livingArea;
        this.purchasePrice = purchasePrice;
        this.rentalPrice = rentalPrice;
        this.afterRepairValue = afterRepairValue;
        this.floodZoneDetermination = floodZoneDetermination;
        this.propertyRented = propertyRented;
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
        this.isManual = isManual;
        this.daysOnMarket = daysOnMarket;
        this.contractExecutionDate = contractExecutionDate;
        this.acquisitionType = acquisitionType;
        this.sourceType = sourceType;
        this.sellerName = sellerName;
        this.sellerContactInfo = sellerContactInfo;
        this.expectedClosingDate = expectedClosingDate;
        this.emdRequirements = emdRequirements;
        this.emdOfferedAmount = emdOfferedAmount;
        this.isMortgage = isMortgage;
        this.distressed = distressed;
        this.lenghOfOwership = lenghOfOwership;
        this.openBalanceMortagage = openBalanceMortagage;
        this.involuntaryLiensAmount = involuntaryLiensAmount;
        this.publicRecord = publicRecord;
        this.mls = mls;
        this.closingCost = closingCost;
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
                request.getPropertyStatus(),
                request.getIsManual(),
                request.getDaysOnMarket(),
                request.getPurchasePrice(),
                request.getRentalPrice(),
                request.getAfterRepairValue(),
                request.getFloodZoneDetermination(),
                request.getPropertyRented(),
                request.getContractExecutionDate(),
                request.getAcquisitionType(),
                request.getSourceType(),
                request.getSellerName(),
                request.getSellerContactInfo(),
                request.getExpectedClosingDate(),
                request.getEmdRequirements(),
                request.getEmdOfferedAmount(),
                request.getDistressed(),
                request.getLenghOfOwership(),
                request.getOpenBalanceMortagage(),
                request.getInvoluntaryLiensAmount(),
                request.getPublicRecord(),
                request.getMls(),
                request.getIsMortgage(),
                request.getBuildingArea(),
                request.getLivingArea(),
                request.getGrossArea(),
                request.getTaxableArea(),
                request.getGarageArea(),
                request.getClosingCost()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyMessage(id);
    }
}
