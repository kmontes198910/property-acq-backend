package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MortageDebtRequest {
    private Integer openMortages;
    private Double estimatedBalance;
    private Integer involuntaryLiens;
    private Double involuntaryAmount;
}
