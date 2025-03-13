package com.kynsof.payment.application.command.groupPayment.updateAdminSystems;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class UpdateGroupPaymentAdminSystemsMessage implements ICommandMessage {

    private final boolean result;

    private final String command = "Update_GROUP_PAYMENT";

    public UpdateGroupPaymentAdminSystemsMessage(boolean result) {
        this.result = result;
    }

}
