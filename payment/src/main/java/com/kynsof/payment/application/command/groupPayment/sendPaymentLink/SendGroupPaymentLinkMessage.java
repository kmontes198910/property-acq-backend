package com.kynsof.payment.application.command.groupPayment.sendPaymentLink;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SendGroupPaymentLinkMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "SEND_GROUP_PAYMENT";

    public SendGroupPaymentLinkMessage(UUID id) {
        this.id = id;
    }

}
