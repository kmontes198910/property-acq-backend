package com.kynsoft.propertyacqcenter.application.command.property.create;

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
public class CreatePropertyRequest {

    private String id;
    private String formattedAddress;
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
    private LocalDate originalContractClosingDate;
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

    private Boolean hasHoa;
    private String hoaName;
    private String hoaType;
    private String hoaFeeFrequency;
}
