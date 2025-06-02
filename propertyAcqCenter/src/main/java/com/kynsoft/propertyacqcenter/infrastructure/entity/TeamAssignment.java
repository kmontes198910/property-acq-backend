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

    private String buyerEntityName;
    private String buyerContactRep;
    private String titleEscrowCompany;
    private String lenderCompany;
    private String projectManager;
    private String legalContact;
    private String seller;
    private String hoa;

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
        this.buyerEntityName = dto.getBuyerEntityName();
        this.buyerContactRep = dto.getBuyerContactRep();
        this.titleEscrowCompany = dto.getTitleEscrowCompany();
        this.lenderCompany = dto.getLenderCompany();
        this.projectManager = dto.getProjectManager();
        this.legalContact = dto.getLegalContact();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.hoa = dto.getHoa();
        this.seller = dto.getSeller();
    }

    public TeamAssignmentDto toAggregate() {
        return TeamAssignmentDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .property(this.property.toAggregateBasic())
                .buyerEntityName(buyerEntityName)
                .buyerContactRep(buyerContactRep)
                .titleEscrowCompany(titleEscrowCompany)
                .lenderCompany(lenderCompany)
                .projectManager(projectManager)
                .legalContact(legalContact)
                .hoa(hoa)
                .seller(seller)
                .build();
    }
}