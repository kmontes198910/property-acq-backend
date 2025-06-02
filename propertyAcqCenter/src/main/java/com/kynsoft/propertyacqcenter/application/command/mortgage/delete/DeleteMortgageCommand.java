package com.kynsoft.propertyacqcenter.application.command.mortgage.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteMortgageCommand implements ICommand {

    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteMortgageMessage(id);
    }
}
