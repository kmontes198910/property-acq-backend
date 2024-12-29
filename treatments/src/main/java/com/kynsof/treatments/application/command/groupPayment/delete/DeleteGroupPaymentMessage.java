package com.kynsof.treatments.application.command.groupPayment.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteGroupPaymentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_GROUP_PAYMENT";

    public DeleteGroupPaymentMessage(UUID id) {
        this.id = id;
    }

}
