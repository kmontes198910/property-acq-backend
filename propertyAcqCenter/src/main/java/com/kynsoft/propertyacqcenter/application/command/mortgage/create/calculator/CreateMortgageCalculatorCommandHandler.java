package com.kynsoft.propertyacqcenter.application.command.mortgage.create.calculator;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.AmortizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateMortgageCalculatorCommandHandler implements ICommandHandler<CreateMortgageCalculatorCommand> {

    private final AmortizationService amortizationService;

    @Override
    public void handle(CreateMortgageCalculatorCommand command) {
        command.setAmortizations(this.amortizationService.generateAmortizationSchedule(MortgageDto
                .builder()
                .purchasePrice(command.getPurchasePrice())
                .downPayment(command.getDownPayment())
                .loanTermYears(command.getLoanTermYears())
                .interestRate(command.getInterestRate())
                .loanStartDate(command.getLoanStartDate())
                .firstPaymentDate(command.getFirstPaymentDate())
                .extraPayments(command.getExtraPayments())
                .extraPaymentAmount(command.getExtraPaymentAmount())
                .build()));
    }

}
