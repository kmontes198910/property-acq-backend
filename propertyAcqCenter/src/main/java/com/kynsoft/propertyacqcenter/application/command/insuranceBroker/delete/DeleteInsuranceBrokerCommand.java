package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteInsuranceBrokerCommand implements ICommand {

    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteInsuranceBrokerMessage(id);
    }
}
