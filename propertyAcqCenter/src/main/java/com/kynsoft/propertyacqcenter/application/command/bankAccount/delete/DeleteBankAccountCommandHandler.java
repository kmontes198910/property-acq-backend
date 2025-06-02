package com.kynsoft.propertyacqcenter.application.command.bankAccount.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import org.springframework.stereotype.Component;

@Component
public class DeleteBankAccountCommandHandler implements ICommandHandler<DeleteBankAccountCommand> {

    private final IBankAccountService bankAccountService;

    public DeleteBankAccountCommandHandler(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void handle(DeleteBankAccountCommand command) {

        bankAccountService.delete(command.getId());
    }

}
