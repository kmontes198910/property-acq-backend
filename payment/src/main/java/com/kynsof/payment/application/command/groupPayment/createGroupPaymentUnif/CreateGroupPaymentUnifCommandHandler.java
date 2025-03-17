package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateGroupPaymentUnifCommandHandler implements ICommandHandler<CreateGroupPaymentUnifCommand> {

    private final IGroupPaymentService serviceImpl;

    public CreateGroupPaymentUnifCommandHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;

    }

    @Override
    public void handle(CreateGroupPaymentUnifCommand command) {
        UUID id = this.serviceImpl.createBillingsAndGroupPayment(command.getClientId(),
                command.getBusinessId(),
                command.getBillings(),
                command.getUserSystemId(),
                command.getUserSystemFullName(),
                command.getPaymentType(),
                command.getPaymentStatus(),
                command.getInsuranceId(),
                command.getTypeOperation()
                , command.isProforma(),
                command.getAuthorizationCode(),
                command.getReference());
        command.setId(id);
    }
}
