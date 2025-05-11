package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.InsuranceDto;
import com.kynsoft.propertyacqcenter.domain.enums.InsuranceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private InsuranceType insuranceType;

    @Column(name = "file_name")
    private String fileName;

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
                .daysSinceCreated(getDaysSinceCreated())
                .daysUntilSixty(this.getDaysUntilSixty())
                .fileName(fileName)
                .build();
    }

    public InsuranceDto toAggregateSimple() {
        return InsuranceDto.builder()
                .id(this.id)
                .document(document)
                .insuranceType(insuranceType)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .fileName(fileName)
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
        this.fileName = dto.getFileName();
    }

    private long getDaysSinceCreated() {
        return ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
    }

    private long getDaysUntilSixty() {
        LocalDateTime sixtyDaysLater = createdAt.plusDays(60);
        return Math.max(0, ChronoUnit.DAYS.between(LocalDateTime.now(), sixtyDaysLater));
    }
}
