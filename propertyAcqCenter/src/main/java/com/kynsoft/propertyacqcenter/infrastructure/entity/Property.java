package com.kynsoft.propertyacqcenter.infrastructure.entity;

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

}