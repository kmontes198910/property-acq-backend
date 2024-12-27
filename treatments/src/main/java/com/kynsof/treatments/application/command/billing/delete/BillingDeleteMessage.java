package com.kynsof.treatments.application.command.billing.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class BillingDeleteMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_MEDICINE";

    public BillingDeleteMessage(UUID id) {
        this.id = id;
    }

}
