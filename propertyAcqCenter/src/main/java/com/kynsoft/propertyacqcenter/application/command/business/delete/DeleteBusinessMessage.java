package com.kynsoft.propertyacqcenter.application.command.business.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteBusinessMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_BUSINESS";

    public DeleteBusinessMessage(UUID id) {
        this.id = id;
    }

}
