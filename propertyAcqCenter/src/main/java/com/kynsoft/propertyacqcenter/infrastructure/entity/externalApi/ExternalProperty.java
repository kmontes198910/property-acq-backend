package com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi;

import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "external_properties")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalProperty {

    @Id
    private UUID id;

    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "formatted_address")
    private String formattedAddress;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    private String city;
    private String state;
    private String zipCode;
    private String county;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int squareFootage;
    private int lotSize;
    private int yearBuilt;
    private String assessorID;
    private String legalDescription;
    private String subdivision;
    private String zoning;
    private String lastSaleDate;
    private int lastSalePrice;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExternalPropertyHOA hoa;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExternalPropertyFeatures features;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, ExternalPropertyTaxAssessment> taxAssessments;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, ExternalPropertyPropertyTax> propertyTaxes;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, ExternalPropertyHistory> history;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExternalPropertyOwner owner;

    private boolean ownerOccupied;

    public ExternalProperty(PropertyResponse property) {
        this.id = UUID.randomUUID();
        this.propertyId = property.getId();
        this.formattedAddress = property.getFormattedAddress();
        this.addressLine1 = property.getAddressLine1();
        this.addressLine2 = property.getAddressLine2();
        this.city = property.getCity();
        this.state = property.getState();
        this.zipCode = property.getZipCode();
        this.county = property.getCounty();
        this.latitude = property.getLatitude();
        this.longitude = property.getLongitude();
        this.propertyType = property.getPropertyType();
        this.bedrooms = property.getBedrooms();
        this.bathrooms = property.getBathrooms();
        this.squareFootage = property.getSquareFootage();
        this.lotSize = property.getLotSize();
        this.yearBuilt = property.getYearBuilt();
        this.assessorID = property.getAssessorID();
        this.legalDescription = property.getLegalDescription();
        this.subdivision = property.getSubdivision();
        this.zoning = property.getZoning();
        this.lastSaleDate = property.getLastSaleDate();
        this.lastSalePrice = property.getLastSalePrice();
        this.hoa = ExternalPropertyHOA.builder()
                .id(UUID.randomUUID())
                .property(this)
                .fee(property.getHoa().getFee())
                .build();
        this.features = ExternalPropertyFeatures.builder()
                .id(UUID.randomUUID())
                .property(this)
                .cooling(property.getFeatures().isCooling())
                .coolingType(property.getFeatures().getCoolingType())
                .exteriorType(property.getFeatures().getExteriorType())
                .floorCount(property.getFeatures().getFloorCount())
                .foundationType(property.getFeatures().getFoundationType())
                .garage(property.getFeatures().isGarage())
                .garageSpaces(property.getFeatures().getGarageSpaces())
                .garageType(property.getFeatures().getGarageType())
                .pool(property.getFeatures().isPool())
                .poolType(property.getFeatures().getPoolType())
                .roofType(property.getFeatures().getRoofType())
                .unitCount(property.getFeatures().getUnitCount())
                .build();
        this.taxAssessments = property.getTaxAssessments().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ExternalPropertyTaxAssessment(
                                UUID.randomUUID(),
                                this,
                                entry.getKey(),
                                entry.getValue().getYear(),
                                entry.getValue().getValue(),
                                entry.getValue().getLand(),
                                entry.getValue().getImprovements()
                        )
                ));
        this.propertyTaxes = property.getPropertyTaxes().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ExternalPropertyPropertyTax(
                                UUID.randomUUID(),
                                this,
                                entry.getKey(),
                                entry.getValue().getYear(),
                                entry.getValue().getTotal()
                        )
                ));
        this.history = property.getHistory().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ExternalPropertyHistory(
                                UUID.randomUUID(),
                                this,
                                entry.getKey(),
                                entry.getValue().getEvent(),
                                entry.getValue().getDate(),
                                entry.getValue().getPrice()
                        )
                ));
        this.owner = new ExternalPropertyOwner(UUID.randomUUID(), this, property.getOwner().getNames(), state, property.getOwner().getMailingAddress());
        this.ownerOccupied = property.isOwnerOccupied();
    }

}
