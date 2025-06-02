package com.kynsoft.propertyacqcenter.application.command.mortgage.update;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMortgageRequest {

    private String property;
    private String mortgageType;//TODO: por definir
    private Double mortgageAmount;
    private Double downPayment;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private LocalDate firstPaymentDate;
    private String compoundFrequency;//TODO: por definir
    private Double balloonPayment;
    private String adjustableRateDetails;//TODO: por definir

}
