package com.kynsof.calendar.application.command.receipt.reverse;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReverseReceiptMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "REVERSE_RECEIPT";

    public ReverseReceiptMessage(UUID id) {
        this.id = id;
    }

}
