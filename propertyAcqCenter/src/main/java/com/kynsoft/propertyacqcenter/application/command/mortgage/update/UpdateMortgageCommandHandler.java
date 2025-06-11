package com.kynsoft.propertyacqcenter.application.command.mortgage.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateMortgageCommandHandler implements ICommandHandler<UpdateMortgageCommand> {

    private final IMortgageService mortgageService;
    private final IPropertyService propertyService;

    public UpdateMortgageCommandHandler(IMortgageService mortgageService, IPropertyService propertyService) {
        this.mortgageService = mortgageService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateMortgageCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.mortgageService.update(MortgageDto.builder()
                .id(command.getId())
                .property(property)
                .mortgageType(command.getMortgageType())
                .mortgageAmount(command.getMortgageAmount())
                .downPayment(command.getDownPayment())
                .fixedRateTermYears(command.getFixedRateTermYears())
                .fixedMortgageRatePercentage(command.getFixedMortgageRatePercentage())
                .firstPaymentDate(command.getFirstPaymentDate())
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
                .extraPaymentAmount(command.getExtraPaymentAmount())
                .build()
        );
    }

}
