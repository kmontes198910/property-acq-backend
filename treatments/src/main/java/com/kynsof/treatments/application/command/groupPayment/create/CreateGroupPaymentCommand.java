package com.kynsof.treatments.application.command.groupPayment.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupPaymentCommand implements ICommand {

    private  UUID id;
    private final List<UUID> billingIds;

    public CreateGroupPaymentCommand(List<UUID> billingIds) {
        this.billingIds = billingIds;
    }


    public static CreateGroupPaymentCommand fromRequest(CreateGroupPaymentRequest request) {
        return new CreateGroupPaymentCommand(
             request.getBillingIds()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateGroupPaymentMessage(id);
    }
}
