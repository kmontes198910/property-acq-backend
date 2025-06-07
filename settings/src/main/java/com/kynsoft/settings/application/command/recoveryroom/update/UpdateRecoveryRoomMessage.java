package com.kynsoft.settings.application.command.recoveryroom.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateRecoveryRoomMessage implements ICommandMessage {
    private UUID id;

    public UpdateRecoveryRoomMessage(UUID id) {
        this.id = id;
    }
}