package com.kynsoft.settings.application.command.recoveryroom.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRecoveryRoomMessage implements ICommandMessage {
    private UUID id;

    public CreateRecoveryRoomMessage(UUID id) {
        this.id = id;
    }
}