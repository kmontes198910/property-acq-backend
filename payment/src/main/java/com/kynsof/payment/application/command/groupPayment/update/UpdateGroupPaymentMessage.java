package com.kynsof.payment.application.command.groupPayment.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class UpdateGroupPaymentMessage implements ICommandMessage {

    private final boolean result;

    private final String command = "Update_GROUP_PAYMENT";

    public UpdateGroupPaymentMessage(boolean result) {
        this.result = result;
    }

}
