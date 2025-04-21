package com.kynsoft.finamer.propertyacqcenter.infrastructure.entity;

import com.kynsoft.finamer.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.finamer.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.enums.EntityStatus;
import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.enums.EntityType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @Column(name = "formation_state", nullable = false)
    private String formationState;

    @Column(name = "formation_date")
    private LocalDateTime formationDate;

    @Column(name = "fiscal_year_end")
    private String fiscalYearEnd;

    @Column(name = "business_description", columnDefinition = "TEXT")
    private String businessDescription;
    
    @Column(name = "registration_number")
    private String registrationNumber;
    
    @Column(name = "website")
    private String website;
    
    @Column(name = "industry")
    private String industry;
    
    @Column(name = "annual_revenue")
    private Double annualRevenue;
    
    @Column(name = "employee_count")
    private Integer employeeCount;
    
    @Column(name = "date_of_last_annual_report")
    private LocalDateTime dateOfLastAnnualReport;
    
    @Column(name = "parent_entity_id")
    private UUID parentEntityId;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status;

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ContactPerson> contactPersons = new ArrayList<>();

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Document> documents = new ArrayList<>();

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
        this.registrationNumber = dto.getRegistrationNumber();
        this.website = dto.getWebsite();
        this.industry = dto.getIndustry();
        this.annualRevenue = dto.getAnnualRevenue();
        this.employeeCount = dto.getEmployeeCount();
        this.dateOfLastAnnualReport = dto.getDateOfLastAnnualReport();
        this.parentEntityId = dto.getParentEntityId();
        this.notes = dto.getNotes();
        this.status = dto.getStatus();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public LegalEntityDto toAggregate() {
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
                .registrationNumber(this.registrationNumber)
                .website(this.website)
                .industry(this.industry)
                .annualRevenue(this.annualRevenue)
                .employeeCount(this.employeeCount)
                .dateOfLastAnnualReport(this.dateOfLastAnnualReport)
                .parentEntityId(this.parentEntityId)
                .notes(this.notes)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}