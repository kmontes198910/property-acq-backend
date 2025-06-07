package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MortgageDto {

    private UUID id;
    private PropertyDto property;

    private MortgageType mortgageType;//TODO: por definir
    private Double mortgageAmount;
    private Double downPayment;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private LocalDate firstPaymentDate;
    private String compoundFrequency;//TODO: por definir
    private Double balloonPayment;
    private String adjustableRateDetails;//TODO: por definir

}
