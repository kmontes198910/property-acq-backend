package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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
    @JoinColumn(name = "company_type_id", nullable = false)
    private CompanyType companyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_company_type_id", nullable = false)
    private SubCompanyType subCompanyType;

    @Column(name = "title")
    private String title;

    @Column(name = "ownership_percentage")
    private Double ownershipPercentage;
    
    @Column(name = "signature_authority")
    private Boolean signatureAuthority;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAddress> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyContact> contacts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

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
        this.companyType = dto.getCompanyType() != null ? new CompanyType(dto.getCompanyType()) : null;
        this.business = dto.getBusiness() != null ? new Business(dto.getBusiness()) : null;
        this.subCompanyType = dto.getSubCompanyType() != null ? new SubCompanyType(dto.getSubCompanyType()) : null;
        this.title = dto.getTitle();
        this.ownershipPercentage = dto.getOwnershipPercentage();
        this.signatureAuthority = dto.getSignatureAuthority();
        this.notes = dto.getNotes();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public CompanyDto toAggregateSimple() {
        return CompanyDto.builder()
                .id(this.id)
                .business(business != null ? this.business.toAggregate() : null)
                .companyType(this.companyType != null ? this.companyType.toAggregate() : null)
                .subCompanyType(subCompanyType != null ? this.subCompanyType.toAggregateSimple() : null)
                .title(this.title)
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
                .title(this.title)
                .subCompanyType(subCompanyType != null ? this.subCompanyType.toAggregate() : null)
                .companyType(this.companyType != null ? this.companyType.toAggregate() : null)
                .ownershipPercentage(this.ownershipPercentage)
                .signatureAuthority(this.signatureAuthority)
                .notes(this.notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

    public CompanyDto toAggregateBasic() {
        return CompanyDto.builder()
                .id(this.id)
                .title(this.title)
                .build();
    }
}
