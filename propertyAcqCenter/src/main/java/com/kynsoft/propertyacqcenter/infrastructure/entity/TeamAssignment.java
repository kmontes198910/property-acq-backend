package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @JoinColumn(name = "buyer_entity_name_id", nullable = true)
    private CompanyContact buyerEntityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_contact_rep_id", nullable = true)
    private CompanyContact buyerContactRep;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_escrow_company_id", nullable = true)
    private CompanyContact titleEscrowCompany;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_company_id", nullable = true)
    private CompanyContact lenderCompany;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager_id", nullable = true)
    private CompanyContact projectManager;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_contact_id", nullable = true)
    private CompanyContact legalContact;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = true)
    private CompanyContact seller;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoa_id", nullable = true)
    private CompanyContact hoa;

    //private String buyerEntityName;
//    private String buyerContactRep;
//    private String titleEscrowCompany;
//    private String lenderCompany;
//    private String projectManager;
//    private String legalContact;
//    private String seller;
//    private String hoa;

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
        this.buyerEntityName = dto.getBuyerEntityName() != null ? new CompanyContact(dto.getBuyerEntityName()) : null;
        this.buyerContactRep = dto.getBuyerContactRep() != null ? new CompanyContact(dto.getBuyerContactRep()) : null;
        this.titleEscrowCompany = dto.getTitleEscrowCompany() != null ? new CompanyContact(dto.getTitleEscrowCompany()) : null;
        this.lenderCompany = dto.getLenderCompany() != null ? new CompanyContact(dto.getLenderCompany()) : null;
        this.projectManager = dto.getProjectManager() != null ? new CompanyContact(dto.getProjectManager()) : null;
        this.legalContact = dto.getLegalContact() != null ? new CompanyContact(dto.getLegalContact()) : null;
        this.hoa = dto.getHoa() != null ? new CompanyContact(dto.getHoa()) : null;
        this.seller = dto.getSeller() != null ? new CompanyContact(dto.getSeller()) : null;
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
                .buyerEntityName(buyerEntityName != null ? buyerEntityName.toAggregate() : null)
                .buyerContactRep(buyerContactRep != null ? buyerContactRep.toAggregate() : null)
                .titleEscrowCompany(titleEscrowCompany != null ? titleEscrowCompany.toAggregate() : null)
                .lenderCompany(lenderCompany != null ? lenderCompany.toAggregate() : null)
                .projectManager(projectManager != null ? projectManager.toAggregate() : null)
                .legalContact(legalContact != null ? legalContact.toAggregate() : null)
                .hoa(hoa != null ? hoa.toAggregate() : null)
                .seller(seller != null ? seller.toAggregate() : null)
                .build();
    }
}