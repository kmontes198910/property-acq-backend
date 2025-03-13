package com.kynsof.payment.application.command.groupPayment.updateAdminSystems;

import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateGroupPaymentAdminSystemsCommandHandler implements ICommandHandler<UpdateGroupPaymentAdminSystemsCommand> {

    private final IGroupPaymentService serviceImpl;

    public UpdateGroupPaymentAdminSystemsCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(UpdateGroupPaymentAdminSystemsCommand command) {

         this.serviceImpl.updateAdminSystems(command.getId(), command.getReference(),
                command.getAuthorizationCode(),command.getPaymentType(),
                command.getStatus());
        command.setResult(true);

    }
}
