package com.kynsof.payment.application.command.groupPayment.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateGroupPaymentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_GROUP_PAYMENT";

    public CreateGroupPaymentMessage(UUID id) {
        this.id = id;
    }

}
