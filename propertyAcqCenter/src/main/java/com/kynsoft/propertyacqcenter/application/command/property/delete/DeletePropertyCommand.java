package com.kynsoft.propertyacqcenter.application.command.property.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePropertyCommand implements ICommand {

    private String id;

    @Override
    public ICommandMessage getMessage() {
        return new DeletePropertyMessage(id);
    }
}
