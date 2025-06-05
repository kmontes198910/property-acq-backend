package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpensesDto implements Serializable {

    private UUID id;
    private PropertyDto property;

    private Double totalAmountExpenses;
    private Double increaseRate;
    private Boolean percentage;
    private Boolean fixedDollarAmount;

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
