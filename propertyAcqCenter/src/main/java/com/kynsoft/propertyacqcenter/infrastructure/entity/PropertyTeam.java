package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyTeamDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.*;

import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "property_teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyTeam {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_contact_id", nullable = false)
    private CompanyContact contact;

    @Column(name = "profile", nullable = false)
    private String profile;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = true)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = true)
    private UUID updatedBy;

    public PropertyTeam(PropertyTeamDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContact(dto.getContact()) : null;
        this.profile = dto.getProfile();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public PropertyTeamDto toAggregate() {
        return PropertyTeamDto
                .builder()
                .id(id)
                .profile(profile)
                .property(property != null ? property.toAggregateBasic() : null)
                .contact(contact != null ? contact.toAggregateTeam() : null)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();
    }
}
