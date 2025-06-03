package com.kynsoft.propertyacqcenter.application.command.income.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import org.springframework.stereotype.Component;

@Component
public class DeleteIncomeCommandHandler implements ICommandHandler<DeleteIncomeCommand> {

    private final IIncomeService incomeService;

    public DeleteIncomeCommandHandler(IIncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public void handle(DeleteIncomeCommand command) {
        this.incomeService.delete(command.getId());
    }
}
