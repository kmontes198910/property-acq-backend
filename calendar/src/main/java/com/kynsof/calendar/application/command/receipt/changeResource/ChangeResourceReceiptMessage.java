package com.kynsof.calendar.application.command.receipt.changeResource;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeResourceReceiptMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_RECEIPT";

    public ChangeResourceReceiptMessage(UUID id) {
        this.id = id;
    }

}
