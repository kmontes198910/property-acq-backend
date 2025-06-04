package com.kynsoft.propertyacqcenter.application.command.expenses.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteExpensesMessage implements ICommandMessage {
    private final String command = "DELETE_EXPENSES";

    private final UUID id;

}
