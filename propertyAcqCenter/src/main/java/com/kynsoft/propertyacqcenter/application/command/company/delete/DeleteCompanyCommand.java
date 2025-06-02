package com.kynsoft.propertyacqcenter.application.command.company.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteCompanyCommand implements ICommand {

    private final UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteCompanyMessage(id);
    }
}
