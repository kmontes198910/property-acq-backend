package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import com.kynsoft.propertyacqcenter.domain.enums.IncreaseType;
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

    @Enumerated(EnumType.STRING)
    private IncreaseType increaseType;

    //Detail Breakdown
    private Double unitType;
    private Double quantity;
    private Double rentMo;
    private Double sqft;//Sqft
    private Double sqftValue;//$/SqFt
    private Double occupancy;
    private Double annualIncrease;
    //private Double avgMonthlyRent;//valor numérico auto calculable, se completa a partir del campo Rent/Mo.

    //Micellaneous income
    private Double depositForfeitures;
    private Double sectino8Income;
    private Double incomefromInterest;
    private Double vendingMachines;
    private Double lateCharges;
    private Double laundryRoom;
    private Double other;

    //Management Fees
    private Double propertyManagementRate;
    private Double leasingCommissionRate;
    private Double leasingCommision;

    private Double porcentageIncreaseType;
    private Double fixedDollarAmount;

    public Income(IncomeDto dto) {
        this.id = dto.getId();
        this.grossMonthlyIncome = dto.getGrossMonthlyIncome();
        this.totalNetMonthlyIncome = dto.getTotalNetMonthlyIncome();
        this.increaseRate = dto.getIncreaseRate();
        this.increaseType = dto.getIncreaseType();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;

        this.unitType = dto.getUnitType();
        this.quantity = dto.getQuantity();
        this.rentMo = dto.getRentMo();
        this.sqft = dto.getSqft();
        this.sqftValue = dto.getSqftValue();
        this.occupancy = dto.getOccupancy();
        this.annualIncrease = dto.getAnnualIncrease();

        this.depositForfeitures = dto.getDepositForfeitures();
        this.sectino8Income = dto.getSectino8Income();
        this.incomefromInterest = dto.getIncomefromInterest();
        this.vendingMachines = dto.getVendingMachines();
        this.lateCharges = dto.getLateCharges();
        this.laundryRoom = dto.getLaundryRoom();
        this.other = dto.getOther();

        this.propertyManagementRate = dto.getPropertyManagementRate();
        this.leasingCommissionRate = dto.getLeasingCommissionRate();
        this.leasingCommision = dto.getLeasingCommision();

        this.porcentageIncreaseType = dto.getPorcentageIncreaseType();
        this.fixedDollarAmount = dto.getFixedDollarAmount();
    }

    public IncomeDto toAggregate() {
        return IncomeDto.builder()
                .id(this.id)
                .property(property.toAggregateBasic())
                .grossMonthlyIncome(grossMonthlyIncome)
                .totalNetMonthlyIncome(totalNetMonthlyIncome)
                .increaseRate(increaseRate)
                .unitType(unitType)
                .quantity(quantity)
                .rentMo(rentMo)
                .sqft(sqft)
                .sqftValue(sqftValue)
                .occupancy(occupancy)
                .annualIncrease(annualIncrease)
                .depositForfeitures(depositForfeitures)
                .sectino8Income(sectino8Income)
                .incomefromInterest(incomefromInterest)
                .vendingMachines(vendingMachines)
                .lateCharges(lateCharges)
                .laundryRoom(laundryRoom)
                .other(other)
                .propertyManagementRate(propertyManagementRate)
                .leasingCommissionRate(leasingCommissionRate)
                .leasingCommision(leasingCommision)
                .porcentageIncreaseType(porcentageIncreaseType)
                .fixedDollarAmount(fixedDollarAmount)
                .increaseType(increaseType)
                .build();
    }
}
