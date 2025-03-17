package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateGroupPaymentUnifMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_GROUP_PAYMENT";

    public CreateGroupPaymentUnifMessage(UUID id) {
        this.id = id;
    }

}
