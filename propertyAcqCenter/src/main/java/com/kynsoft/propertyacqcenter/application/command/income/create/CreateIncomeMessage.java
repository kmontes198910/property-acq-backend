package com.kynsoft.propertyacqcenter.application.command.income.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateIncomeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_INCOME";

    public CreateIncomeMessage(UUID id) {
        this.id = id;
    }

}
