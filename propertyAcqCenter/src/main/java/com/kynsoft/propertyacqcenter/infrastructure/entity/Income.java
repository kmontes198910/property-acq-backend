package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "income")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Income implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    private Double grossMonthlyIncome;
    private Double totalNetMonthlyIncome;
    private Double increaseRate;
    private Boolean increaseTypePercentage;
    private Boolean increaseFixedDollarAmount;

    public Income(IncomeDto dto) {
        this.id = dto.getId();
        this.grossMonthlyIncome = dto.getGrossMonthlyIncome();
        this.totalNetMonthlyIncome = dto.getTotalNetMonthlyIncome();
        this.increaseRate = dto.getIncreaseRate();
        this.increaseTypePercentage = dto.getIncreaseTypePercentage();
        this.increaseFixedDollarAmount = dto.getIncreaseFixedDollarAmount();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
    }

    public IncomeDto toAggregate() {
        return IncomeDto.builder()
                .id(this.id)
                .property(property.toAggregateBasic())
                .grossMonthlyIncome(grossMonthlyIncome)
                .totalNetMonthlyIncome(totalNetMonthlyIncome)
                .increaseRate(increaseRate)
                .increaseTypePercentage(increaseTypePercentage)
                .increaseFixedDollarAmount(increaseFixedDollarAmount)
                .build();
    }
}
