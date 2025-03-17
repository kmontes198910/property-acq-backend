package com.kynsof.payment.application.command.billing.delete;

import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.service.IBillingService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteBillingCommandHandler implements ICommandHandler<DeleteBillingCommand> {

    private final IBillingService serviceImpl;

    public DeleteBillingCommandHandler(IBillingService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteBillingCommand command) {
        BillingDto delete = this.serviceImpl.findById(command.getId());
        serviceImpl.delete(delete);
    }

}
