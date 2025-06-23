package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GeneralDocument> documents = new HashSet<>();

    @Column(name = "buyer_name_and_year_vehicle", nullable = true)
    private String buyerNameAndYearVehicle;

    @Column(name = "buyer_license_tag_no", nullable = true)
    private String buyerLicenseTagNo;

    ///
    @Column(name = "date_and_time_for_inspections", nullable = true)
    private LocalDate dateAndTimeForInspections;

    @Column(name = "instructions_for_access", nullable = true)
    private String instructionsForAccess;

    @Column(name = "hoa_buyer_interview_date", nullable = true)
    private LocalDate hoaBuyerInterviewDate;

    @Column(name = "preferred_move_in_date", nullable = true)
    private LocalDate preferredMoveinDate;

    @Column(name = "e_sign_authorization", nullable = true)
    private String eSignAuthorization;

    @Column(name = "final_walkthrough_date", nullable = true)
    private LocalDate finalWalkthroughDate;

    @Column(name = "wire_account_holder_name", nullable = true)
    private String wireAccountHolderName;

    @Column(name = "wire_account_name", nullable = true)
    private String wireAccountNumber;

    @Column(name = "wire_routing_number", nullable = true)
    private String wireRoutingNumber;

    @Column(name = "zelle_email_or_phone", nullable = true)
    private String zelleEmailorPhone;

    @Column(name = "electric_provider_confirmation", nullable = true)
    private String electricProviderConfirmation;

    @Column(name = "gas_service_confirmation", nullable = true)
    private String gasServiceConfirmation;

    @Column(name = "trash_service_confirmation", nullable = true)
    private String trashServiceConfirmation;

    @Column(name = "water_sewer_setup_confirmation", nullable = true)
    private String waterSewerSetupConfirmation;

    ///
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

        this.dateAndTimeForInspections = dto.getDateAndTimeForInspections();
        this.instructionsForAccess = dto.getInstructionsForAccess();
        this.hoaBuyerInterviewDate = dto.getHoaBuyerInterviewDate();
        this.preferredMoveinDate = dto.getPreferredMoveinDate();
        this.eSignAuthorization = dto.getESignAuthorization();
        this.finalWalkthroughDate = dto.getFinalWalkthroughDate();
        this.wireAccountHolderName = dto.getWireAccountHolderName();
        this.wireAccountNumber = dto.getWireAccountNumber();
        this.wireRoutingNumber = dto.getWireRoutingNumber();
        this.zelleEmailorPhone = dto.getZelleEmailorPhone();
        this.electricProviderConfirmation = dto.getElectricProviderConfirmation();
        this.gasServiceConfirmation = dto.getGasServiceConfirmation();
        this.trashServiceConfirmation = dto.getTrashServiceConfirmation();
        this.waterSewerSetupConfirmation = dto.getWaterSewerSetupConfirmation();

        if (dto.getDocuments() != null) {
            dto.getDocuments().forEach(x -> {
                GeneralDocument doc = new GeneralDocument(x);
                doc.setAdquisitionProperty(this);
                this.documents.add(doc);
            });
        }

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
                .dateAndTimeForInspections(dateAndTimeForInspections)
                .instructionsForAccess(instructionsForAccess)
                .hoaBuyerInterviewDate(hoaBuyerInterviewDate)
                .preferredMoveinDate(preferredMoveinDate)
                .eSignAuthorization(eSignAuthorization)
                .finalWalkthroughDate(finalWalkthroughDate)
                .wireAccountHolderName(wireAccountHolderName)
                .wireAccountNumber(wireAccountNumber)
                .wireRoutingNumber(wireRoutingNumber)
                .zelleEmailorPhone(zelleEmailorPhone)
                .electricProviderConfirmation(electricProviderConfirmation)
                .gasServiceConfirmation(gasServiceConfirmation)
                .trashServiceConfirmation(trashServiceConfirmation)
                .waterSewerSetupConfirmation(waterSewerSetupConfirmation)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)

                .documents(documents != null ? documents.stream().map(GeneralDocument::toAggregateSimple).collect(Collectors.toList()) : null)
                .build();
    }

    public AdquisitionPropertyDto toAggregateBasic() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .build();
    }
}
