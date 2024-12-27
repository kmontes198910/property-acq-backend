package com.kynsof.calendar.application.command.receipt.setRequest;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SetRequestIdMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CONFIRM_PAYMENT";

    public SetRequestIdMessage(UUID id) {
        this.id = id;
    }

}
