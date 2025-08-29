package com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.dto.AmortizationScheduleDto;
import lombok.Getter;

@Getter
public class CreateMortgageCalculatorMessage implements ICommandMessage {
    private final AmortizationScheduleDto amortizations;

    public CreateMortgageCalculatorMessage(AmortizationScheduleDto amortizations) {
        this.amortizations = amortizations;
    }

}
