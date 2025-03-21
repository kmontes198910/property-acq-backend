package com.kynsof.payment.application.command.groupPayment.update;

import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateGroupPaymentCommandHandler implements ICommandHandler<UpdateGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;

    public UpdateGroupPaymentCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(UpdateGroupPaymentCommand command) {

        try {
            this.serviceImpl.update(command.getId(), command.getReference(),
                   command.getAuthorizationCode(),command.getRequestId(),
                   command.getProcessUrl(),
                   command.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        command.setResult(true);

    }
}
