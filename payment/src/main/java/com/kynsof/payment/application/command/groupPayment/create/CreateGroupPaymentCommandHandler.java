package com.kynsof.payment.application.command.groupPayment.create;

import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateGroupPaymentCommandHandler implements ICommandHandler<CreateGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;

    public CreateGroupPaymentCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(CreateGroupPaymentCommand command) {
        UUID id = this.serviceImpl.createGroupPayment(
                command.getBillingIds(), 
                command.getBusinessId(),
                command.getPatientsId()
        );
        command.setId(id);
    }
}
