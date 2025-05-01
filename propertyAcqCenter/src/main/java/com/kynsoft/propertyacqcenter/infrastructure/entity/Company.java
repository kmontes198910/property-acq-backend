package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = false)
    private LegalEntity legalEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_type_id", nullable = false)
    private CompanyType companyType;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ContactRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "title")
    private String title;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "personal_tax_id")
    private String personalTaxId;
    
    @Column(name = "nationality")
    private String nationality;
    
    @Column(name = "personal_address")
    private String personalAddress;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "zip_code")
    private String zipCode;
    
    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "ownership_percentage")
    private Double ownershipPercentage;
    
    @Column(name = "signature_authority")
    private Boolean signatureAuthority;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

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

    public Company(CompanyDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntity(dto.getLegalEntity()) : null;
        this.companyType = dto.getCompanyType() != null ? new CompanyType(dto.getCompanyType()) : null;
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.role = dto.getRole();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.cellPhone = dto.getCellPhone();
        this.title = dto.getTitle();
        this.dateOfBirth = dto.getDateOfBirth();
        this.personalTaxId = dto.getPersonalTaxId();
        this.nationality = dto.getNationality();
        this.personalAddress = dto.getPersonalAddress();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.personalEmail = dto.getPersonalEmail();
        this.isPrimary = dto.getIsPrimary();
        this.ownershipPercentage = dto.getOwnershipPercentage();
        this.signatureAuthority = dto.getSignatureAuthority();
        this.notes = dto.getNotes();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public CompanyDto toAggregateSimple() {
        return CompanyDto.builder()
                .id(this.id)
                .legalEntity(this.legalEntity != null ? this.legalEntity.toAggregateFindById() : null)
                .companyType(this.companyType != null ? this.companyType.toAggregate() : null)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .role(this.role)
                .email(this.email)
                .phone(this.phone)
                .cellPhone(this.cellPhone)
                .title(this.title)
                .dateOfBirth(this.dateOfBirth)
                .personalTaxId(this.personalTaxId)
                .nationality(this.nationality)
                .personalAddress(this.personalAddress)
                .city(this.city)
                .state(this.state)
                .zipCode(this.zipCode)
                .personalEmail(this.personalEmail)
                .isPrimary(this.isPrimary)
                .ownershipPercentage(this.ownershipPercentage)
                .signatureAuthority(this.signatureAuthority)
                .notes(this.notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

    public CompanyDto toAggregate() {
        return CompanyDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .role(this.role)
                .email(this.email)
                .phone(this.phone)
                .cellPhone(this.cellPhone)
                .title(this.title)
                .dateOfBirth(this.dateOfBirth)
                .personalTaxId(this.personalTaxId)
                .nationality(this.nationality)
                .personalAddress(this.personalAddress)
                .city(this.city)
                .state(this.state)
                .zipCode(this.zipCode)
                .personalEmail(this.personalEmail)
                .isPrimary(this.isPrimary)
                .ownershipPercentage(this.ownershipPercentage)
                .signatureAuthority(this.signatureAuthority)
                .notes(this.notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}