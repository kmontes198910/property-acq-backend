package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "properties")
public class Property {

    @Id
    private UUID id;

    @Embedded
    private EmbeddableAddress address;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean distressed;
    private Boolean shortSale;
    private Boolean hoa;
    private Boolean ownerOccupied;
    private String occupancy;
    private Integer squareFeet;
    private Integer lotSize;
    private Integer yearBuilt;
    private int bedrooms;
    private int bathrooms;
    private String lengthOfOwnership;
    private String purchaseMethod;
    private String apn;
    private String description;
    private Double estimatedValue;
    private Double lastRecordedValue;
    private Double lastSalePrice;
    private LocalDate lastSaleDate;
    private String lastDocumentType;

    private BigDecimal equity;
    private BigDecimal monthlyRentEstimate;
    private Double grossYield;

    @Embedded
    private Owner owner;

    @Embedded
    private Features features;

    @Embedded
    private Sale sale;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TaxAssessment> taxAssessments;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Mortgage> mortgages;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<History> history;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lien> liens;

    public Property(PropertyDto propertyDto){
        this.id = propertyDto.getId();
        this.address = propertyDto.getAddress() != null ? new EmbeddableAddress(propertyDto.getAddress()) : null;
        this.propertyType = propertyDto.getPropertyType();
        this.status = propertyDto.getStatus();
        this.distressed = propertyDto.getDistressed();
        this.shortSale = propertyDto.getShortSale();
        this.hoa = propertyDto.getHoa();
        this.ownerOccupied = propertyDto.getOwnerOccupied();
        this.occupancy = propertyDto.getOccupancy();
        this.squareFeet = propertyDto.getSquareFeet();
        this.lotSize = propertyDto.getLotSize();
        this.yearBuilt = propertyDto.getYearBuilt();
        this.bedrooms = propertyDto.getBedrooms();
        this.bathrooms = propertyDto.getBathrooms();
        this.lengthOfOwnership = propertyDto.getLengthOfOwnership();
        this.purchaseMethod = propertyDto.getPurchaseMethod();
        this.apn = propertyDto.getApn();
        this.description = propertyDto.getDescription();
        this.estimatedValue = propertyDto.getEstimatedValue();
        this.lastRecordedValue = propertyDto.getLastRecordedValue();
        this.lastSalePrice = propertyDto.getLastSalePrice();
        this.lastSaleDate = propertyDto.getLastSaleDate();
        this.lastDocumentType = propertyDto.getLastDocumentType();
        this.equity = propertyDto.getEquity();
        this.monthlyRentEstimate = propertyDto.getMonthlyRentEstimate();
        this.grossYield = propertyDto.getGrossYield();
        this.owner = propertyDto.getOwner() != null ? new Owner(propertyDto.getOwner()) : null;
        this.features = propertyDto.getFeatures() != null ? new Features(propertyDto.getFeatures()) : null;
        this.sale = propertyDto.getSale() != null ? new Sale(propertyDto.getSale()) : null;
        this.taxAssessments = propertyDto.getTaxAssessments().stream()
                .map(TaxAssessment::new)
                .toList();
        this.mortgages = propertyDto.getMortgages().stream()
                .map(Mortgage::new)
                .toList();
        this.history = propertyDto.getHistory().stream()
                .map(History::new)
                .toList();
        this.liens = propertyDto.getLiens().stream()
                .map(Lien::new)
                .toList();
    }

    public PropertyDto toAggregate() {
        return PropertyDto.builder()
                .id(this.id)
                .address(this.address != null ? this.address.toAggregate() : null)
                .propertyType(this.propertyType)
                .status(this.status)
                .distressed(this.distressed)
                .shortSale(this.shortSale)
                .hoa(this.hoa)
                .ownerOccupied(this.ownerOccupied)
                .occupancy(this.occupancy)
                .squareFeet(this.squareFeet)
                .lotSize(this.lotSize)
                .yearBuilt(this.yearBuilt)
                .bedrooms(this.bedrooms)
                .bathrooms(this.bathrooms)
                .lengthOfOwnership(this.lengthOfOwnership)
                .purchaseMethod(this.purchaseMethod)
                .apn(this.apn)
                .description(this.description)
                .estimatedValue(this.estimatedValue)
                .lastRecordedValue(this.lastRecordedValue)
                .lastSalePrice(this.lastSalePrice)
                .lastSaleDate(this.lastSaleDate)
                .lastDocumentType(this.lastDocumentType)
                .equity(this.equity)
                .monthlyRentEstimate(this.monthlyRentEstimate)
                .grossYield(this.grossYield)
                .owner(owner != null ? this.owner.toAggregate() : null)
                .features(features != null ? this.features.toAggregate() : null)
                .sale(sale != null ? this.sale.toAggregate() : null)
                .taxAssessments(taxAssessments.stream()
                        .map(TaxAssessment::toAggregate).toList())
                .mortgages(mortgages.stream()
                        .map(Mortgage::toAggregate).toList())
                .history(history.stream()
                        .map(History::toAggregate).toList())
                .liens(liens.stream()
                        .map(Lien::toAggregate).toList())
                .build();
    }
}