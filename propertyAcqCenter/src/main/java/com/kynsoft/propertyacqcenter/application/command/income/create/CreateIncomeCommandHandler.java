package com.kynsoft.propertyacqcenter.application.command.income.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreateIncomeCommandHandler implements ICommandHandler<CreateIncomeCommand> {

    private final IIncomeService incomeService;
    private final IPropertyService propertyService;

    public CreateIncomeCommandHandler(IIncomeService incomeService, IPropertyService propertyService) {
        this.incomeService = incomeService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateIncomeCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.incomeService.create(IncomeDto.builder()
                .id(command.getId())
                .property(property)
                .grossMonthlyIncome(command.getGrossMonthlyIncome())
                .totalNetMonthlyIncome(command.getTotalNetMonthlyIncome())
                .increaseRate(command.getIncreaseRate())
                .increaseFixedDollarAmount(command.getIncreaseFixedDollarAmount())
                .increaseTypePercentage(command.getIncreaseTypePercentage())
                .build()
        );
    }

}
