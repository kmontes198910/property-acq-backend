package com.kynsoft.settings.application.command.recoverybed.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateRecoveryBedMessage implements ICommandMessage {
    private final UUID id;

    public CreateRecoveryBedMessage(UUID id) {
        this.id = id;
    }
}