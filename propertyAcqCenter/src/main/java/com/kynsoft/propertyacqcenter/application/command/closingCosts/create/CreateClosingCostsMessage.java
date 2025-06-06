package com.kynsoft.propertyacqcenter.application.command.closingCosts.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateClosingCostsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_CLOSING_COST";

    public CreateClosingCostsMessage(UUID id) {
        this.id = id;
    }

}
