package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.InsuranceBrokerDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "insurance_broker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceBroker {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private LegalEntity buyer;

    @Column(name = "closing_date", nullable = false)
    private LocalDate closingDate;

    @Column(name = "lender_info")
    private String lenderInfo;

    @Column(name = "lender_insurance")
    private String lenderInsurance;

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

    public InsuranceBroker(InsuranceBrokerDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.buyer = dto.getBuyer() != null ? new LegalEntity(dto.getBuyer()) : null;
        this.closingDate = dto.getClosingDate();
        this.lenderInfo = dto.getLenderInfo();
        this.lenderInsurance = dto.getLenderInsurance();
    }

    public InsuranceBrokerDto toAggregate() {
        return InsuranceBrokerDto.builder()
                .id(this.id)
                .property(property != null ? property.toAggregateBasic() : null)
                .buyer(buyer != null ? buyer.toAggregateBasic() : null)
                .closingDate(closingDate)
                .lenderInfo(lenderInfo)
                .lenderInsurance(lenderInsurance)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}
