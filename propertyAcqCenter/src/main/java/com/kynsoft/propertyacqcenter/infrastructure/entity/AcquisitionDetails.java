package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AcquisitionDetailsDto;
import com.kynsoft.propertyacqcenter.domain.enums.AcquisitionType;
import com.kynsoft.propertyacqcenter.domain.enums.SourceType;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "acquisition_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcquisitionDetails {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_name_id", nullable = false)
    private LegalEntity sellerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company sellerContactInfo;

    @Column(name = "contract_execution_date", nullable = false)
    private LocalDate contractExecutionDate;

    @Column(name = "expected_closing_date", nullable = false)
    private LocalDate expectedClosingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "acquisition_type", nullable = false)
    private AcquisitionType acquisitionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false)
    private SourceType sourceType;

    @Column(name = "asking_price")
    private Double askingPrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @Column(name = "emd_offered_amount")
    private Double emdOfferedAmount;

    @Column(name = "after_repair_value")
    private Double afterRepairValue;

    @Column(name = "emd_requirements")
    private Boolean emdRequirements;

    @Column(name = "flood_zone_determination")
    private Boolean floodZoneDetermination;

    @Column(name = "property_rented")
    private Boolean propertyRented;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    public AcquisitionDetails(AcquisitionDetailsDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.sellerName = dto.getSellerName() != null ? new LegalEntity(dto.getSellerName()) : null;
        this.sellerContactInfo = dto.getSellerContactInfo() != null ? new Company(dto.getSellerContactInfo()) : null;
        this.contractExecutionDate = dto.getContractExecutionDate();
        this.acquisitionType = dto.getAcquisitionType();
        this.sourceType = dto.getSourceType();
        this.askingPrice = dto.getAskingPrice();
        this.purchasePrice = dto.getPurchasePrice();
        this.expectedClosingDate = dto.getExpectedClosingDate();
        this.rentalPrice = dto.getRentalPrice();
        this.emdRequirements = dto.getEmdRequirements();
        this.emdOfferedAmount = dto.getEmdOfferedAmount();
        this.afterRepairValue = dto.getAfterRepairValue();
        this.floodZoneDetermination = dto.getFloodZoneDetermination();
        this.propertyRented = dto.getPropertyRented();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
    }

    public AcquisitionDetailsDto toAggregate() {
        return AcquisitionDetailsDto.builder()
                .id(this.id)
                .contractExecutionDate(contractExecutionDate)
                .acquisitionType(acquisitionType)
                .sourceType(sourceType)
                .sellerName(sellerName != null ? sellerName.toAggregateBasic() : null)
                .sellerContactInfo(sellerContactInfo != null ? sellerContactInfo.toAggregateBasic() : null)
                .property(property != null ? property.toAggregateBasic() : null)
                .askingPrice(askingPrice)
                .purchasePrice(purchasePrice)
                .expectedClosingDate(expectedClosingDate)
                .rentalPrice(rentalPrice)
                .emdRequirements(emdRequirements)
                .emdOfferedAmount(emdOfferedAmount)
                .afterRepairValue(afterRepairValue)
                .floodZoneDetermination(floodZoneDetermination)
                .propertyRented(propertyRented)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}
