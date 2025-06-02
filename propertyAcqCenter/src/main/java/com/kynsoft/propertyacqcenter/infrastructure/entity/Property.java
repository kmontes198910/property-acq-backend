package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyStatus;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "roof_type", nullable = true)
    private String roofType;
    @Column(name = "structure_type", nullable = true)
    private String structureType;

    private String hoa;
    private Integer bedrooms;
    private Double bathrooms;
    private Double askingPrice;
    private Double purchasePrice;
    private Double rentalPrice;
    private Double afterRepairValue;
    private Boolean floodZoneDetermination;
    private Boolean propertyRented;
    private Integer daysOnMarket;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "property_status", nullable = true)
//    private PropertyStatus propertyStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_property", nullable = true)
    private PropertyStatus propertyStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private Boolean isManual;

    //Acquisition
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_name_id", nullable = true)
    private LegalEntity sellerName;//

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = true)
    private Contact sellerContactInfo;//

    @Column(name = "contract_execution_date", nullable = true)
    private LocalDate contractExecutionDate;//

    @Column(name = "expected_closing_date", nullable = true)
    private LocalDate expectedClosingDate;//

    @Enumerated(EnumType.STRING)
    @Column(name = "acquisition_type", nullable = true)
    private AcquisitionType acquisitionType;//

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = true)
    private SourceType sourceType;//

    @Column(name = "emd_offered_amount", nullable = true)
    private Double emdOfferedAmount;//

    @Column(name = "emd_requirements", nullable = true)
    private Boolean emdRequirements;//

    //Mortagage
    @Column(name = "distressed", nullable = true)
    private String distressed;//

    @Column(name = "lengh_of_owership", nullable = true)
    private String lenghOfOwership;//

    @Column(name = "open_balance_mortagage", nullable = true)
    private Double openBalanceMortagage;//

    @Column(name = "involuntary_liens_amount", nullable = true)
    private Double involuntaryLiensAmount;//

    //last sale
    @Column(name = "public_record", nullable = true)
    private Double publicRecord;//

    @Column(name = "mls", nullable = true)
    private Double mls;//

    public Property(PropertyDto dto) {
        this.id = dto.getId();
        this.purchasePrice = dto.getPurchasePrice();
        this.rentalPrice = dto.getRentalPrice();
        this.afterRepairValue = dto.getAfterRepairValue();
        this.floodZoneDetermination = dto.getFloodZoneDetermination();
        this.propertyRented = dto.getPropertyRented();
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
        this.propertyStatus = dto.getPropertyStatus();
        this.isManual = dto.getIsManual();
        this.daysOnMarket = dto.getDaysOnMarket();

        //Acquisition
        this.sellerName = dto.getSellerName() != null ? new LegalEntity(dto.getSellerName()) : null;
        this.sellerContactInfo = dto.getSellerContactInfo() != null ? new Contact(dto.getSellerContactInfo()) : null;
        this.contractExecutionDate = dto.getContractExecutionDate();
        this.acquisitionType = dto.getAcquisitionType();
        this.sourceType = dto.getSourceType();
        this.expectedClosingDate = dto.getExpectedClosingDate();
        this.emdRequirements = dto.getEmdRequirements();
        this.emdOfferedAmount = dto.getEmdOfferedAmount();

        //Mortagage
        this.distressed = dto.getDistressed();
        this.lenghOfOwership = dto.getLenghOfOwership();
        this.openBalanceMortagage = dto.getOpenBalanceMortagage();
        this.involuntaryLiensAmount = dto.getInvoluntaryLiensAmount();

        //last sale
        this.publicRecord = dto.getPublicRecord();
        this.mls = dto.getMls();
    }

    public PropertyDto toAggregateBasic() {
        return PropertyDto.builder()
                .id(this.id)
                .formattedAddress(formattedAddress)
                .build();
    }

    public PropertyDto toAggregate() {
        return PropertyDto.builder()
                .id(this.id)
                .purchasePrice(purchasePrice)
                .rentalPrice(rentalPrice)
                .afterRepairValue(afterRepairValue)
                .floodZoneDetermination(floodZoneDetermination)
                .propertyRented(propertyRented)
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
                .propertyStatus(propertyStatus)
                .createdAt(createdAt)
                .isManual(isManual)
                .daysOnMarket(daysOnMarket)

                .contractExecutionDate(contractExecutionDate)
                .acquisitionType(acquisitionType)
                .sourceType(sourceType)
                .sellerName(sellerName != null ? sellerName.toAggregateBasic() : null)
                .sellerContactInfo(sellerContactInfo != null ? sellerContactInfo.toAggregateBasic() : null)
                .expectedClosingDate(expectedClosingDate)
                .emdRequirements(emdRequirements)
                .emdOfferedAmount(emdOfferedAmount)

                .distressed(distressed)
                .lenghOfOwership(lenghOfOwership)
                .openBalanceMortagage(openBalanceMortagage)
                .involuntaryLiensAmount(involuntaryLiensAmount)
                .publicRecord(publicRecord)
                .mls(mls)
                .build();
    }

}