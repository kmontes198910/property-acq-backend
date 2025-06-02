package com.kynsoft.propertyacqcenter.application.command.mortgage.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateMortgageMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_MORTGAGE";

    public CreateMortgageMessage(UUID id) {
        this.id = id;
    }

}
