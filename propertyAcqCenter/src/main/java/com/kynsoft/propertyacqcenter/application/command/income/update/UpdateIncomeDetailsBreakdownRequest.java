package com.kynsoft.propertyacqcenter.application.command.income.update;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateIncomeDetailsBreakdownRequest implements Serializable {

    private UUID id;
    private Double unitType;
    private Double quantity;
    private Double rentMo;
    private Double sqft;//Sqft
    private Double sqftValue;//$/SqFt
    private Double occupancy;
    private Double annualIncrease;
}
