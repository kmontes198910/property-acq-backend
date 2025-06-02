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
                .mortgageAmount(command.getMortgageAmount())
                .downPayment(command.getDownPayment())
                .fixedRateTermYears(command.getFixedRateTermYears())
                .fixedMortgageRatePercentage(command.getFixedMortgageRatePercentage())
                .firstPaymentDate(command.getFirstPaymentDate())
                .compoundFrequency(command.getCompoundFrequency())
                .balloonPayment(command.getBalloonPayment())
                .adjustableRateDetails(command.getAdjustableRateDetails())
                .build()
        );
    }

}
