package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String roofType;
    private String structureType;
    private String hoa;
    private Integer bedrooms;
    private Double bathrooms;
    private Double askingPrice;
    private String formattedAddress;
    private PropertyStatus propertyStatus;
    private LocalDateTime createdAt;
    private Boolean isManual;
    private Integer daysOnMarket;

    private Double purchasePrice;
    private Double rentalPrice;
    private Double afterRepairValue;
    private Boolean floodZoneDetermination;
    private Boolean propertyRented;

    //Acquisition
    private LocalDate contractExecutionDate;
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.
    private LegalEntityBasicResponse sellerName;//Relacion con Legal entity
    private ContactBasicResponse sellerContactInfo;//Relacion con Legal entity
    private LocalDate expectedClosingDate;
    private Boolean emdRequirements;
    private Double emdOfferedAmount;

    //Mortagage
    private String distressed;//
    private String lenghOfOwership;//
    private Double openBalanceMortagage;//
    private Double involuntaryLiensAmount;//

    //last sale
    private Double publicRecord;//
    private Double mls;//

    public PropertiesResponse(PropertyDto dto) {
        this.purchasePrice = dto.getPurchasePrice();
        this.rentalPrice = dto.getRentalPrice();
        this.afterRepairValue = dto.getAfterRepairValue();
        this.floodZoneDetermination = dto.getFloodZoneDetermination();
        this.propertyRented = dto.getPropertyRented();
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
        this.formattedAddress = dto.getFormattedAddress();
        this.propertyStatus = dto.getPropertyStatus();
        this.createdAt = dto.getCreatedAt();
        this.isManual = dto.getIsManual();
        this.daysOnMarket = dto.getDaysOnMarket();

        //Acquisition
        this.contractExecutionDate = dto.getContractExecutionDate();
        this.acquisitionType = dto.getAcquisitionType();
        this.sourceType = dto.getSourceType();
        this.sellerName = dto.getSellerName() != null ? new LegalEntityBasicResponse(dto.getSellerName()) : null;
        this.sellerContactInfo = dto.getSellerContactInfo() != null ? new ContactBasicResponse(dto.getSellerContactInfo()) : null;
        this.expectedClosingDate = dto.getExpectedClosingDate();
        this.emdRequirements = dto.getEmdRequirements();
        this.emdOfferedAmount = dto.getEmdOfferedAmount();

        this.distressed = dto.getDistressed();
        this.lenghOfOwership = dto.getLenghOfOwership();
        this.openBalanceMortagage = dto.getOpenBalanceMortagage();
        this.involuntaryLiensAmount = dto.getInvoluntaryLiensAmount();

        this.publicRecord = dto.getPublicRecord();
        this.mls = dto.getMls();
    }

}