package com.kynsoft.propertyacqcenter.application.command.expenses.create;

import com.kynsoft.propertyacqcenter.domain.enums.IncreaseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExpensesRequest {

    private String property;
    private Double totalAmountExpenses;
    private Double increaseRate;
    private Boolean percentage;
    private Boolean fixedDollarAmount;
    private IncreaseType increaseType;

    private Double accounting;
    private Double electricity;
    private Double gas;
    private Double mortgageInsurance;
    private Double poolSpaService;
    private Double sewerWater;
    private Double trash;
    private Double advertising;
    private Double fireInsurance;
    private Double janitorialService;
    private Double liabilityInsurance;
    private Double otherUtilities;
    private Double propertyTaxes;
    private Double supplies;
    private Double workmans;
    private Double associationFees;
    private Double floodInsurance;
    private Double landscaping;
    private Double licenses;
    private Double payroll;
    private Double repairMaintenance;
    private Double telephone;
    private Double miscellaneous;
    private Double legal;
}
