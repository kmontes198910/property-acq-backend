package com.kynsoft.propertyacqcenter.application.command.mortgage.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMortgageCommand implements ICommand {

    private UUID id;
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

    public CreateMortgageCommand(String property, MortgageType mortgageType, Double mortgageAmount, 
                                 Double downPayment, Integer fixedRateTermYears, Double fixedMortgageRatePercentage, 
                                 LocalDate firstPaymentDate, MortgageFrequencyInterestCompounded compoundFrequency, 
                                 Boolean balloonPayment, Boolean adjustableRateDetails, Integer fixedRateTermMonths,
                                 
                                 String adjustableRateType, String hybridArmType, Double fixedRateTerm,
                                 Double rateChangeInterval, Double expectedRateChange, Double limitRate,
                                 Double limitIncrease, Double howManyPayments, Boolean accelerationWeeklyPayments, Boolean accelerationExtraPayments) {
        this.id = UUID.randomUUID();
        this.adjustableRateType = adjustableRateType;
        this.hybridArmType= hybridArmType;
        this.fixedRateTerm = fixedRateTerm;
        this.rateChangeInterval = rateChangeInterval;
        this.expectedRateChange = expectedRateChange;
        this.limitRate = limitRate;
        this.limitIncrease = limitIncrease;
        this.howManyPayments = howManyPayments;
        this.accelerationWeeklyPayments = accelerationWeeklyPayments;
        this.accelerationExtraPayments = accelerationExtraPayments;
        this.property = property;
        this.mortgageType = mortgageType;
        this.mortgageAmount = mortgageAmount;
        this.downPayment = downPayment;
        this.fixedRateTermYears = fixedRateTermYears;
        this.fixedMortgageRatePercentage = fixedMortgageRatePercentage;
        this.firstPaymentDate = firstPaymentDate;
        this.compoundFrequency = compoundFrequency;
        this.balloonPayment = balloonPayment;
        this.adjustableRateDetails = adjustableRateDetails;
        this.fixedRateTermMonths = fixedRateTermMonths;
    }

    public static CreateMortgageCommand fromRequest(CreateMortgageRequest request) {
        return new CreateMortgageCommand(
                request.getProperty(),
                request.getMortgageType(),
                request.getMortgageAmount(),
                request.getDownPayment(),
                request.getFixedRateTermYears(),
                request.getFixedMortgageRatePercentage(),
                request.getFirstPaymentDate(),
                request.getCompoundFrequency(),
                request.getBalloonPayment(),
                request.getAdjustableRateDetails(),
                request.getFixedRateTermMonths(),
                request.getAdjustableRateType(),
                request.getHybridArmType(),
                request.getFixedRateTerm(),
                request.getRateChangeInterval(),
                request.getExpectedRateChange(),
                request.getLimitRate(),
                request.getLimitIncrease(),
                request.getHowManyPayments(),
                request.getAccelerationWeeklyPayments(),
                request.getAccelerationExtraPayments()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateMortgageMessage(id);
    }
}
