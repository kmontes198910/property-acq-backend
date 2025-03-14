package com.kynsof.payment.application.command.groupPayment.delete;

import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteGroupPaymentCommandHandler implements ICommandHandler<DeleteGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;

    public DeleteGroupPaymentCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteGroupPaymentCommand command) {
        this.serviceImpl.delete(command.getId());
    }

}
