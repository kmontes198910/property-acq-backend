package com.kynsoft.propertyacqcenter.application.command.income.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateIncomeRequest {

    private String property;
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
