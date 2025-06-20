package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adquisition_property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionProperty {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = true)
    private LegalEntity buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_contact_id", nullable = true)
    private CompanyContact contact;

    @Column(name = "buyer_name_and_year_vehicle", nullable = true)
    private String buyerNameAndYearVehicle;

    @Column(name = "buyer_license_tag_no", nullable = true)
    private String buyerLicenseTagNo;

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

    public AdquisitionProperty(AdquisitionPropertyDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.buyerNameAndYearVehicle = dto.getBuyerNameAndYearVehicle();
        this.buyerLicenseTagNo = dto.getBuyerLicenseTagNo();
        this.buyer = dto.getBuyer() != null ? new LegalEntity(dto.getBuyer()) : null;
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContact(dto.getContact()) : null;
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public AdquisitionPropertyDto toAggregate() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .buyer(this.buyer != null ? this.buyer.toAggregateBasic() : null)
                .property(this.property != null ? this.property.toAggregateBasic() : null)
                .contact(this.contact != null ? this.contact.toAggregate() : null)
                .buyerNameAndYearVehicle(buyerNameAndYearVehicle)
                .buyerLicenseTagNo(buyerLicenseTagNo)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

    public AdquisitionPropertyDto toAggregateBasic() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .build();
    }
}