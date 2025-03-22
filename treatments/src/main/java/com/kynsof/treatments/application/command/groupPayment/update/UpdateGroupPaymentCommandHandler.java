package com.kynsof.treatments.application.command.groupPayment.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.service.IGroupPaymentService;
import org.springframework.stereotype.Component;

@Component
public class UpdateGroupPaymentCommandHandler implements ICommandHandler<UpdateGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;

    public UpdateGroupPaymentCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(UpdateGroupPaymentCommand command) {

         this.serviceImpl.update(command.getId(), command.getReference(),
                command.getAuthorizationCode(),command.getRequestId(),
                command.getProcessUrl(),
                command.getStatus());
        command.setResult(true);

    }
}
