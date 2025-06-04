package com.kynsoft.propertyacqcenter.application.command.expenses.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateExpensesMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EXPENSES";

    public CreateExpensesMessage(UUID id) {
        this.id = id;
    }

}
