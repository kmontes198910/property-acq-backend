package com.kynsoft.propertyacqcenter.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyTeam {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "company_contact_id", nullable = false)
    private UUID companyContactId;

    @Column(name = "company_contact_id", nullable = false, insertable = false, updatable = false)
    private CompanyContact companyContact;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    @Column(name = "property_id", nullable = false, insertable = false, updatable = false)
    private Property property;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;
    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;
}
