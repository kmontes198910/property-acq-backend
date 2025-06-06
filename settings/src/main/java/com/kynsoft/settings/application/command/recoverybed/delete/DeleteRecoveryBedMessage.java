package com.kynsoft.settings.application.command.recoverybed.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteRecoveryBedMessage implements ICommandMessage {
    private final UUID id;

    public DeleteRecoveryBedMessage(UUID id) {
        this.id = id;
    }
}