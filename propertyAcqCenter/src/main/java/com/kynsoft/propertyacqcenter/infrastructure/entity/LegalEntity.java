package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import com.kynsoft.propertyacqcenter.domain.enums.Month;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "legal_entities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegalEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;

    @Column(name = "entity_experience")
    private String entityExperience;

    @Column(name = "entity_fico")
    private Float entityFico;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @Column(name = "formation_state", nullable = false)
    private String formationState;

    @Column(name = "formation_date")
    private LocalDate formationDate;

    @Column(name = "fiscal_year_end")
    private Month fiscalYearEnd;

    @Column(name = "business_description", columnDefinition = "TEXT")
    private String businessDescription;

    @Column(name = "website")
    private String website;

    @Column(name = "annual_revenue")
    private Double annualRevenue;

    @Column(name = "authorized_signer_government_id_copy")
    private String authorizedSignerGovernmentIdCopy;

    @Column(name = "authorized_signer_government_id_copy_file_name")
    private String authorizedSignerGovernmentIdCopyFileName;

    @Column(name = "date_of_last_annual_report")
    private LocalDate dateOfLastAnnualReport;

//    @Column(name = "parent_entity_id")
//    private UUID parentEntityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_entity_id",
            foreignKey = @ForeignKey(name = "fk_legal_entity_parent"))
    private LegalEntity parent;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "owner")
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contactPersons;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> bankAccounts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

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

    public LegalEntity(LegalEntityDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.name = dto.getName();
        this.taxId = dto.getTaxId();
        this.entityType = dto.getEntityType();
        this.business = dto.getBusiness() != null ? new Business(dto.getBusiness().getId(), dto.getBusiness().getName()) : null;
        this.formationState = dto.getFormationState();
        this.formationDate = dto.getFormationDate();
        this.fiscalYearEnd = dto.getFiscalYearEnd();
        this.businessDescription = dto.getBusinessDescription();
        this.website = dto.getWebsite();
        this.annualRevenue = dto.getAnnualRevenue();
        this.dateOfLastAnnualReport = dto.getDateOfLastAnnualReport();
        this.parent = dto.getParentEntityId() != null ? new LegalEntity(dto.getParentEntityId()) : null;
        this.notes = dto.getNotes();
        this.status = dto.getStatus();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.owner = dto.getOwner();
        this.entityFico = dto.getEntityFico();
        this.entityExperience = dto.getEntityExperience();
        this.authorizedSignerGovernmentIdCopy = dto.getAuthorizedSignerGovernmentIdCopy();
        this.authorizedSignerGovernmentIdCopyFileName = dto.getAuthorizedSignerGovernmentIdCopyFileName();
    }

    public LegalEntityDto toAggregate() {
        return LegalEntityDto.builder()
                .id(this.id)
                .name(this.name)
                .taxId(this.taxId)
                .entityType(this.entityType)
                .formationState(this.formationState)
                .formationDate(this.formationDate)
                .fiscalYearEnd(this.fiscalYearEnd)
                .businessDescription(this.businessDescription)
                .website(this.website)
                .annualRevenue(this.annualRevenue)
                .dateOfLastAnnualReport(this.dateOfLastAnnualReport)
                .parentEntityId(this.parent != null ? this.parent.toAggregateBasic() : null)
                .notes(this.notes)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .owner(owner)
                .entityExperience(entityExperience)
                .entityFico(entityFico)
                .authorizedSignerGovernmentIdCopy(authorizedSignerGovernmentIdCopy)
                .authorizedSignerGovernmentIdCopyFileName(authorizedSignerGovernmentIdCopyFileName)
                .build();
    }

    public LegalEntityDto toAggregateBasic() {
        return LegalEntityDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public LegalEntityDto toAggregateFindById() {
        return LegalEntityDto.builder()
                .id(this.id)
                .name(this.name)
                .taxId(this.taxId)
                .entityType(this.entityType)
                .business(this.business != null ? BusinessDto.builder()
                        .id(this.business.getId())
                        .name(this.business.getName())
                        .createdAt(this.business.getCreatedAt())
                        .updatedAt(this.business.getUpdatedAt())
                        .build() : null)
                .formationState(this.formationState)
                .formationDate(this.formationDate)
                .fiscalYearEnd(this.fiscalYearEnd)
                .businessDescription(this.businessDescription)
                .website(this.website)
                .annualRevenue(this.annualRevenue)
                .dateOfLastAnnualReport(this.dateOfLastAnnualReport)
                .parentEntityId(this.parent != null ? this.parent.toAggregateBasic() : null)
                .notes(this.notes)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .owner(owner)
                .entityExperience(entityExperience)
                .entityFico(entityFico)
                .authorizedSignerGovernmentIdCopy(authorizedSignerGovernmentIdCopy)
                .authorizedSignerGovernmentIdCopyFileName(authorizedSignerGovernmentIdCopyFileName)
                .build();
    }
}
