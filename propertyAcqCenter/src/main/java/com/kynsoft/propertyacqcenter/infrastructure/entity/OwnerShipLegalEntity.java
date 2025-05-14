package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.OwnerShipLegalEntityDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "owner_legal_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerShipLegalEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ownership_percentage")
    private Double ownershipPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity")
    private LegalEntity legalEntity;

    public OwnerShipLegalEntity(OwnerShipLegalEntityDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.ownershipPercentage = dto.getOwnershipPercentage();
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntity(dto.getLegalEntity()) : null;
    }

    public OwnerShipLegalEntityDto toAggregate() {
        return OwnerShipLegalEntityDto.builder()
                .id(this.id)
                .name(name)
                .ownershipPercentage(ownershipPercentage)
                .legalEntity(this.legalEntity != null ? this.legalEntity.toAggregateBasic() : null)
                .build();
    }
}
