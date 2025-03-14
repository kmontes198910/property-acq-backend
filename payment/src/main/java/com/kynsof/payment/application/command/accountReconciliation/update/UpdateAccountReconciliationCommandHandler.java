package com.kynsof.payment.application.command.accountReconciliation.update;

import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.service.IAccountReconciliationService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAccountReconciliationCommandHandler implements ICommandHandler<UpdateAccountReconciliationCommand> {

    private final IAccountReconciliationService serviceImpl;

    public UpdateAccountReconciliationCommandHandler(IAccountReconciliationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(UpdateAccountReconciliationCommand command) {
        AccountReconciliationDto object = this.serviceImpl.findById(command.getId());
        object.setCode(command.getCode());
        object.setDescription(command.getDescription());
        object.setCost(command.getCost());
        this.serviceImpl.update(object);
    }
}
