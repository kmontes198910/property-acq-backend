package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.InsuranceDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "insurance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insurance {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "insurance_type", nullable = false)
    private String insuranceType;

    @Column(name = "document", nullable = false)
    private String document;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    public InsuranceDto toAggregate() {
        return InsuranceDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .document(document)
                .insuranceType(insuranceType)
                .updatedAt(this.updatedAt)
                .legalEntity(this.legalEntity != null ? this.legalEntity.toAggregateBasic() : null)
                .build();
    }

    public InsuranceDto toAggregateSimple() {
        return InsuranceDto.builder()
                .id(this.id)
                .document(document)
                .insuranceType(insuranceType)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .legalEntity(this.legalEntity != null ? this.legalEntity.toAggregateFindById() : null)
                .build();
    }

    public Insurance(InsuranceDto dto) {
        this.id = dto.getId();
        this.document = dto.getDocument();
        this.insuranceType = dto.getInsuranceType();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.legalEntity = new LegalEntity(dto.getLegalEntity());
    }
}
