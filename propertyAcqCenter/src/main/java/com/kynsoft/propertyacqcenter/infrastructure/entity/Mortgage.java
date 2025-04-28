package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "mortgage_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Mortgage implements Serializable {

    @Id
    private UUID id;

    private Double estimatedBalance;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    public Mortgage(MortgageDto mortgageDto) {
        this.id = mortgageDto.getId();
        this.estimatedBalance = mortgageDto.getEstimatedBalance();
    }

    public MortgageDto toAggregate() {
        return MortgageDto.builder()
                .id(this.id)
                .estimatedBalance(this.estimatedBalance)
                .build();
    }
}