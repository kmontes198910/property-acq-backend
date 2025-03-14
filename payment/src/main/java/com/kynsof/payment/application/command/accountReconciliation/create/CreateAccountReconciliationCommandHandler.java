package com.kynsof.payment.application.command.accountReconciliation.create;

import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.service.IAccountReconciliationService;
import com.kynsof.payment.domain.service.IBusiness;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountReconciliationCommandHandler implements ICommandHandler<CreateAccountReconciliationCommand> {

    private final IAccountReconciliationService serviceImpl;
    private final IBusiness businessService;

    public CreateAccountReconciliationCommandHandler(IAccountReconciliationService serviceImpl, IBusiness businessService) {
        this.serviceImpl = serviceImpl;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateAccountReconciliationCommand command) {
        BusinessDto businessDto = this.businessService.findById(command.getBusiness());
        this.serviceImpl.create(new AccountReconciliationDto(
                command.getId(),
                command.getCode(),
                command.getDescription(),
                command.getCost(),
                businessDto
        ));
    }
}
