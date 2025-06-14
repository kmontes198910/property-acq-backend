package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "team_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamAssignment {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = true)
    private LegalEntity buyerEntityName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_buyer_contacts",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> buyerContactReps = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_title_escrow_companies",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> titleEscrowCompanies = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_lender_companies",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> lenderCompanies = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_project_managers",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> projectManagers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_legal_contacts",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> legalContacts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_sellers",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> sellers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_assignment_hoas",
            joinColumns = @JoinColumn(name = "team_assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "company_contact_id")
    )
    private Set<CompanyContact> hoas = new HashSet<>();

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

    public TeamAssignment(TeamAssignmentDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.buyerEntityName = dto.getBuyerEntityName() != null ? new LegalEntity(dto.getBuyerEntityName()) : null;
        this.buyerContactReps = dto.getBuyerContactReps() != null
                ? dto.getBuyerContactReps().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.titleEscrowCompanies = dto.getTitleEscrowCompany() != null
                ? dto.getTitleEscrowCompany().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.lenderCompanies = dto.getLenderCompany() != null
                ? dto.getLenderCompany().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.projectManagers = dto.getProjectManager() != null
                ? dto.getProjectManager().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.legalContacts = dto.getLegalContact() != null
                ? dto.getLegalContact().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.hoas = dto.getHoa() != null
                ? dto.getHoa().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.sellers = dto.getSeller() != null
                ? dto.getSeller().stream().map(CompanyContact::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public TeamAssignmentDto toAggregate() {
        return TeamAssignmentDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .property(this.property.toAggregateBasic())
                .buyerEntityName(buyerEntityName != null ? buyerEntityName.toAggregateBasic() : null)
                .buyerContactReps(buyerContactReps != null ? buyerContactReps.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .titleEscrowCompany(titleEscrowCompanies != null ? titleEscrowCompanies.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .lenderCompany(lenderCompanies != null ? lenderCompanies.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .projectManager(projectManagers != null ? projectManagers.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .legalContact(legalContacts != null ? legalContacts.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .hoa(hoas != null ? hoas.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .seller(sellers != null ? sellers.stream().map(CompanyContact::toAggregate).collect(Collectors.toList()) : null)
                .build();
    }
}
