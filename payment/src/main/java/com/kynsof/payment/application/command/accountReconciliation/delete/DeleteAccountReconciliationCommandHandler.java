package com.kynsof.payment.application.command.accountReconciliation.delete;

import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.service.IAccountReconciliationService;
import org.springframework.stereotype.Component;

@Component
public class DeleteAccountReconciliationCommandHandler implements ICommandHandler<DeleteAccountReconciliationCommand> {

    private final IAccountReconciliationService serviceImpl;

    public DeleteAccountReconciliationCommandHandler(IAccountReconciliationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteAccountReconciliationCommand command) {
        AccountReconciliationDto delete = this.serviceImpl.findById(command.getId());
        serviceImpl.delete(delete);
    }

}
