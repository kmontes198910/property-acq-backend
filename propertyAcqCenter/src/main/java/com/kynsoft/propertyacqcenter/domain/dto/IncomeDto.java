package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IncomeDto implements Serializable {

    private UUID id;
    private PropertyDto property;
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

}
