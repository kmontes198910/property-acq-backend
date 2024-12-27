package com.kynsof.treatments.application.command.billing.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateBillingMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_MEDICINE";

    public UpdateBillingMessage(UUID id) {
        this.id = id;
    }

}
