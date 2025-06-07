package com.kynsoft.settings.application.command.recoverybed.changestatus;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChangeRecoveryBedStatusCommand implements ICommand {
    private UUID id;
    private UUID recoveryBedId;
    private String status;
    private UUID updatedBy;

    public ChangeRecoveryBedStatusCommand() {
        this.id = UUID.randomUUID();
    }

    public ChangeRecoveryBedStatusCommand(UUID recoveryBedId, String status) {
        this.id = UUID.randomUUID();
        this.recoveryBedId = recoveryBedId;
        this.status = status;
    }

    @Override
    public ICommandMessage getMessage() {
        return new ChangeRecoveryBedStatusMessage(id);
    }
}