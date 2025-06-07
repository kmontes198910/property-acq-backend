package com.kynsoft.settings.application.command.recoverybed.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeRecoveryBedStatusMessage implements ICommandMessage {
    private final UUID id;

    public ChangeRecoveryBedStatusMessage(UUID id) {
        this.id = id;
    }
}