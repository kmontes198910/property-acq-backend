package com.kynsoft.propertyacqcenter.application.command.mortgage.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMortgageCommand implements ICommand {

    private UUID id;
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

    public UpdateMortgageCommand(UUID id, String property, String mortgageType, Double mortgageAmount, 
                                 Double downPayment, Integer fixedRateTermYears, Double fixedMortgageRatePercentage, 
                                 LocalDate firstPaymentDate, String compoundFrequency, Double balloonPayment, String adjustableRateDetails) {
        this.id = id;
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
    }

    public static UpdateMortgageCommand fromRequest(UpdateMortgageRequest request, UUID id) {
        return new UpdateMortgageCommand(
                id,
                request.getProperty(),
                request.getMortgageType(),
                request.getMortgageAmount(),
                request.getDownPayment(),
                request.getFixedRateTermYears(),
                request.getFixedMortgageRatePercentage(),
                request.getFirstPaymentDate(),
                request.getCompoundFrequency(),
                request.getBalloonPayment(),
                request.getAdjustableRateDetails()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateMortgageMessage(id);
    }
}
