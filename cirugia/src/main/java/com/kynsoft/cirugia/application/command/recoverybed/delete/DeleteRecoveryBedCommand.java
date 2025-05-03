package com.kynsoft.cirugia.application.command.recoverybed.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteRecoveryBedCommand implements ICommand {

    private UUID id;
    private UUID recoveryBedId;

    public DeleteRecoveryBedCommand(UUID recoveryBedId) {
        this.id = UUID.randomUUID();
        this.recoveryBedId = recoveryBedId;
    }

    @Override
    public ICommandMessage getMessage() {
        return new DeleteRecoveryBedMessage(id);
    }
}