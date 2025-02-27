package com.kynsof.payment.application.command.billing.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateBillingMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_MEDICINE";

    public CreateBillingMessage(UUID id) {
        this.id = id;
    }

}
