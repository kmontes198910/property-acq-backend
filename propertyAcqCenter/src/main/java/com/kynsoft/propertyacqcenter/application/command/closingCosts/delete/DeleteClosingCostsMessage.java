package com.kynsoft.propertyacqcenter.application.command.closingCosts.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteClosingCostsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_CLOSING_COST";

    public DeleteClosingCostsMessage(UUID id) {
        this.id = id;
    }

}
