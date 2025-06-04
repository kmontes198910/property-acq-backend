package com.kynsoft.propertyacqcenter.application.command.expenses.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpensesCommandHandler implements ICommandHandler<DeleteExpensesCommand> {

    private final IExpensesService expensesService;

    public DeleteExpensesCommandHandler(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public void handle(DeleteExpensesCommand command) {
        this.expensesService.delete(command.getId());
    }
}
