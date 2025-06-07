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

    public CreateMortgageCommand(String property, MortgageType mortgageType, Double mortgageAmount, 
                                 Double downPayment, Integer fixedRateTermYears, Double fixedMortgageRatePercentage, 
                                 LocalDate firstPaymentDate, MortgageFrequencyInterestCompounded compoundFrequency, 
                                 Boolean balloonPayment, Boolean adjustableRateDetails, Integer fixedRateTermMonths) {
        this.id = UUID.randomUUID();
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
                request.getFixedRateTermMonths()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateMortgageMessage(id);
    }
}
