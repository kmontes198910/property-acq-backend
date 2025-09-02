package com.kynsoft.propertyacqcenter.application.command.mortgage.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreateMortgageCommandHandler implements ICommandHandler<CreateMortgageCommand> {

    private final IMortgageService mortgageService;
    private final IPropertyService propertyService;

    public CreateMortgageCommandHandler(IMortgageService mortgageService, IPropertyService propertyService) {
        this.mortgageService = mortgageService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateMortgageCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.mortgageService.create(MortgageDto.builder()
                .id(command.getId())
                .property(property)
                .mortgageType(command.getMortgageType())
                .fixedRateTermYears(command.getFixedRateTermYears())
                .fixedMortgageRatePercentage(command.getFixedMortgageRatePercentage())
                .compoundFrequency(command.getCompoundFrequency())
                .balloonPayment(command.getBalloonPayment())
                .adjustableRateDetails(command.getAdjustableRateDetails())
                .fixedRateTermMonths(command.getFixedRateTermMonths())
                .adjustableRateType(command.getAdjustableRateType())
                .hybridArmType(command.getHybridArmType())
                .fixedRateTerm(command.getFixedRateTerm())
                .rateChangeInterval(command.getRateChangeInterval())
                .expectedRateChange(command.getExpectedRateChange())
                .limitRate(command.getLimitRate())
                .limitIncrease(command.getLimitIncrease())
                .howManyPayments(command.getHowManyPayments())
                .accelerationWeeklyPayments(command.getAccelerationWeeklyPayments())
                .accelerationExtraPayments(command.getAccelerationExtraPayments())
                .lifetimeRateCap(command.getLifetimeRateCap())
                .extraPaymentFrequency(command.getExtraPaymentFrequency())

                .purchasePrice(command.getPurchasePrice())
                .downPayment(command.getDownPayment())
                .isPercentage(command.getIsPercentage())
                .loanTermYears(command.getLoanTermYears())
                .interestRate(command.getInterestRate())
                .loanStartDate(command.getLoanStartDate())
                .firstPaymentDate(command.getFirstPaymentDate())
                .extraPayments(command.getExtraPayments())
                .extraPaymentAmount(command.getExtraPaymentAmount())
                .build()
        );
    }

}
