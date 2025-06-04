package com.kynsoft.propertyacqcenter.application.command.expenses.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateExpensesMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_EXPENSES";

    public UpdateExpensesMessage(UUID id) {
        this.id = id;
    }

}
