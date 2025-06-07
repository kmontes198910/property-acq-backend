package com.kynsoft.settings.application.command.recoverybed.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateRecoveryBedMessage implements ICommandMessage {
    private final UUID id;

    public UpdateRecoveryBedMessage(UUID id) {
        this.id = id;
    }
}