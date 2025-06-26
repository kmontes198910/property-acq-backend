package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class PropertyDto {

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
    private PropertyStatus propertyStatus;
    private LocalDateTime createdAt;
    private Boolean isManual;
    private Integer daysOnMarket;

    private Double purchasePrice;//
    private Double rentalPrice;//
    private Double afterRepairValue;//
    private Boolean floodZoneDetermination;//
    private Boolean propertyRented;//
    private Double askingPrice;//

    //Acquisition
    private LocalDate contractExecutionDate;//
    private AcquisitionType acquisitionType; // Purchase, Assignment, Inherited, JV//
    private SourceType sourceType; // Broker, Wholesaler, Direct-to-Seller, etc.//
    private LegalEntityDto sellerName;//Relacion con Legal entity//
    private ContactDto sellerContactInfo;//Relacion con Contact//
    private Boolean emdRequirements;//
    private Double emdOfferedAmount;//
    private LocalDate expectedClosingDate;//

    //Mortagage
    private Boolean distressed;//
    private Boolean isMortgage;//
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

    //HOA
    private Boolean hasHoa;
    private String hoaName;
    private String hoaType;
    private String hoaFeeFrequency;
    private List<TeamAssignmentDto> teamAssignments;
}