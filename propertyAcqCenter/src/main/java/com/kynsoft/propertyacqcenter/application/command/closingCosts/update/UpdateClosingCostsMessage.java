package com.kynsoft.propertyacqcenter.application.command.closingCosts.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateClosingCostsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_CLOSING_COST";

    public UpdateClosingCostsMessage(UUID id) {
        this.id = id;
    }

}
