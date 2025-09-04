package com.kynsoft.propertyacqcenter.application.command.mortgage.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.LoanType;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageExtraPaymentFrequency;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageLifetimeRateCap;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMortgageCommand implements ICommand {

    private UUID id;
    private String property;
    private MortgageType mortgageType;
    private Double mortgageAmount;
    private Double downPayment;
    private LocalDate firstPaymentDate;
    private MortgageFrequencyInterestCompounded compoundFrequency;
    private Boolean balloonPayment;
    private Boolean adjustableRateDetails;

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
    private Boolean hasLoan;
    private LoanType loanType;
    private Boolean isPercentage;

    public UpdateMortgageCommand(UUID id, String property, MortgageType mortgageType, Double mortgageAmount, 
                                 Double downPayment, LocalDate firstPaymentDate, MortgageFrequencyInterestCompounded compoundFrequency, 
                                 Boolean balloonPayment, Boolean adjustableRateDetails,
                                 String adjustableRateType, String hybridArmType, Double fixedRateTerm,
                                 Double rateChangeInterval, Double expectedRateChange, Double limitRate,
                                 Double limitIncrease, Double howManyPayments, Boolean accelerationWeeklyPayments, 
                                 Boolean accelerationExtraPayments, MortgageLifetimeRateCap lifetimeRateCap,
                                 MortgageExtraPaymentFrequency extraPaymentFrequency, Double extraPaymentAmount,
                                 Boolean hasLoan, LoanType loanType, Boolean isPercentage) {
        this.id = id;
        this.extraPaymentFrequency = extraPaymentFrequency;
        this.extraPaymentAmount = extraPaymentAmount;
        this.property = property;
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
        this.mortgageType = mortgageType;
        this.mortgageAmount = mortgageAmount;
        this.downPayment = downPayment;
        this.firstPaymentDate = firstPaymentDate;
        this.compoundFrequency = compoundFrequency;
        this.balloonPayment = balloonPayment;
        this.adjustableRateDetails = adjustableRateDetails;
        this.lifetimeRateCap = lifetimeRateCap;
        this.hasLoan = hasLoan;
        this.loanType = loanType;
        this.isPercentage = isPercentage;
    }

    public static UpdateMortgageCommand fromRequest(UpdateMortgageRequest request, UUID id) {
        return new UpdateMortgageCommand(
                id,
                request.getProperty(),
                request.getMortgageType(),
                request.getMortgageAmount(),
                request.getDownPayment(),
                request.getFirstPaymentDate(),
                request.getCompoundFrequency(),
                request.getBalloonPayment(),
                request.getAdjustableRateDetails(),
                request.getAdjustableRateType(),
                request.getHybridArmType(),
                request.getFixedRateTerm(),
                request.getRateChangeInterval(),
                request.getExpectedRateChange(),
                request.getLimitRate(),
                request.getLimitIncrease(),
                request.getHowManyPayments(),
                request.getAccelerationWeeklyPayments(),
                request.getAccelerationExtraPayments(),
                request.getLifetimeRateCap(),
                request.getExtraPaymentFrequency(),
                request.getExtraPaymentAmount(),
                request.getHasLoan(),
                request.getLoanType(),
                request.getIsPercentage()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateMortgageMessage(id);
    }
}
