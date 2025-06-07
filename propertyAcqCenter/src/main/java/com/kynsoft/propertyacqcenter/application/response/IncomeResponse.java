package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IncomeResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;
    private Double grossMonthlyIncome;
    private Double totalNetMonthlyIncome;
    private Double increaseRate;
    private Boolean increaseTypePercentage;
    private Boolean increaseFixedDollarAmount;

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

    public IncomeResponse(IncomeDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.grossMonthlyIncome = dto.getGrossMonthlyIncome();
        this.totalNetMonthlyIncome = dto.getTotalNetMonthlyIncome();
        this.increaseRate = dto.getIncreaseRate();
        this.increaseTypePercentage = dto.getIncreaseTypePercentage();
        this.increaseFixedDollarAmount = dto.getIncreaseFixedDollarAmount();

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

}
