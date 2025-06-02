package com.kynsoft.propertyacqcenter.application.command.mortgage.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateMortgageMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_MORTGAGE";

    public UpdateMortgageMessage(UUID id) {
        this.id = id;
    }

}
