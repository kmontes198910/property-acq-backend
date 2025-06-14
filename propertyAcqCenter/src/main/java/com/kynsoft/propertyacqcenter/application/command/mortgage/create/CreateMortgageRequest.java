package com.kynsoft.propertyacqcenter.application.command.mortgage.create;

import com.kynsoft.propertyacqcenter.domain.enums.MortgageExtraPaymentFrequency;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageLifetimeRateCap;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMortgageRequest {

    private String property;
    private MortgageType mortgageType;
    private Double mortgageAmount;
    private Double downPayment;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private LocalDate firstPaymentDate;
    private MortgageFrequencyInterestCompounded compoundFrequency;
    private Boolean balloonPayment;
    private Boolean adjustableRateDetails;
    private Integer fixedRateTermMonths;

    private String adjustableRateType;
    private String hybridArmType;
    private Double fixedRateTerm;
    private Double rateChangeInterval;
    private Double expectedRateChange;
    private Double limitRate;
    private Double limitIncrease;

    private Double howManyPayments;

    private Boolean accelerationWeeklyPayments;
    private Boolean accelerationExtraPayments;
    private MortgageLifetimeRateCap lifetimeRateCap;//

    private MortgageExtraPaymentFrequency extraPaymentFrequency;//
    private Double extraPaymentAmount;//

}
