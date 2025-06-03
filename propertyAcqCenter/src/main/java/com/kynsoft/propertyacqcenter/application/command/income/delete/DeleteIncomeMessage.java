package com.kynsoft.propertyacqcenter.application.command.income.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteIncomeMessage implements ICommandMessage {
    private final String command = "DELETE_INCOME";

    private final UUID id;

}
