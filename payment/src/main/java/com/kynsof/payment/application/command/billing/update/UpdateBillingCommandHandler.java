package com.kynsof.payment.application.command.billing.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.service.IBillingService;
import org.springframework.stereotype.Component;

@Component
public class UpdateBillingCommandHandler implements ICommandHandler<UpdateBillingCommand> {

    private final IBillingService serviceImpl;

    public UpdateBillingCommandHandler(IBillingService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(UpdateBillingCommand command) {

        BillingDto update = this.serviceImpl.findById(command.getId());
        update.setCode(command.getCode());
        update.setStatus(command.getStatus());
        update.setDescription(command.getDescription());
        update.setProforma(command.isProforma());
        update.setCost(command.getCost());

        this.serviceImpl.update(update);
    }
}
