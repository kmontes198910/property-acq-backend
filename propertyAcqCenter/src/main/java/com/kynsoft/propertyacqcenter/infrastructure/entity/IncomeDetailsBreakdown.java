package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.IncomeDetailsBreakdownDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "income_details_breakdown")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IncomeDetailsBreakdown implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_id")
    private Income income;

    //Detail Breakdown
    private Double unitType;
    private Double quantity;
    private Double rentMo;
    private Double sqft;//Sqft
    private Double sqftValue;//$/SqFt
    private Double occupancy;
    private Double annualIncrease;
    //private Double avgMonthlyRent;//valor numérico auto calculable, se completa a partir del campo Rent/Mo.

    public IncomeDetailsBreakdown(IncomeDetailsBreakdownDto dto) {
        this.id = dto.getId();
        this.income = dto.getIncome() != null ? new Income(dto.getIncome()) : null;

        this.unitType = dto.getUnitType();
        this.quantity = dto.getQuantity();
        this.rentMo = dto.getRentMo();
        this.sqft = dto.getSqft();
        this.sqftValue = dto.getSqftValue();
        this.occupancy = dto.getOccupancy();
        this.annualIncrease = dto.getAnnualIncrease();
    }

    public IncomeDetailsBreakdownDto toAggregate() {
        return IncomeDetailsBreakdownDto.builder()
                .id(this.id)
                .unitType(unitType)
                .quantity(quantity)
                .rentMo(rentMo)
                .sqft(sqft)
                .sqftValue(sqftValue)
                .occupancy(occupancy)
                .annualIncrease(annualIncrease)
                .build();
    }
}
