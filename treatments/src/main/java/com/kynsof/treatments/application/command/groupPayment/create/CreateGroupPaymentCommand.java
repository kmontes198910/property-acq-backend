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
    private final UUID businessId;
    private final UUID patientsId;

    public CreateGroupPaymentCommand(List<UUID> billingIds, UUID businessId, UUID patientsId) {
        this.billingIds = billingIds;
        this.businessId = businessId;
        this.patientsId = patientsId;
    }


    public static CreateGroupPaymentCommand fromRequest(CreateGroupPaymentRequest request) {
        return new CreateGroupPaymentCommand(
             request.getBillingIds(),
                request.getBusinessId(),
                request.getPatientsId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateGroupPaymentMessage(id);
    }
}
