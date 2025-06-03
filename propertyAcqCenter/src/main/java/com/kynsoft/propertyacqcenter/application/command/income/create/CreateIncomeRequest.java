package com.kynsoft.propertyacqcenter.application.command.income.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateIncomeRequest {

    private String property;
    private Double grossMonthlyIncome;
    private Double totalNetMonthlyIncome;
    private Double increaseRate;
    private Boolean increaseTypePercentage;
    private Boolean increaseFixedDollarAmount;
}
